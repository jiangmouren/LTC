"""
Food delivery companies employ tens of thousands of delivery drivers who each submit hundreds of deliveries per week.
Delivery details are automatically sent to the system immediately after the delivery.
Delivery drivers have different hourly payment rates, depending on their performance.
Drivers can take on, and be paid for, multiple deliveries simultaneously.
If a driver is paid $10.00 per hour, and a delivery takes 1 hour and 30 minutes, the driver is paid $15.00 for that delivery.
We are building a dashboard to show a single number - the total cost of all deliveries - on screens in the accounting department offices.
At first, we want the following functions:
Q1 - ---
add_driver(driver_id[integer], usd_hourly_rate[float])
The given driver will not already be in the system
record_delivery(driver_id[integer], start_time, end_time)
Discuss the time format you choose
Times require minimum one-second precision
The given driver will already be in the system
All deliveries will be recorded immediately after the delivery is completed
No delivery will exceed 3 hours
get_total_cost()
Return the total, aggregated cost of all drivers' deliveries recorded in the system
For example, return 135.30 if one driver is in the system and has a total cost of 100.30 USD and another driver is in the system and has a total cost of 35.00 USD.
This will be used for a live dashboard
Do not worry about exact formatting

All inputs will be valid.
Share any decisions or assumptions you make.
If you do anything differently in this interview than you would in production, share that.
Before coding, let's discuss how you will store the time data and why.
We want to see good OOP practices.
You may look up syntax using a search engine, as long as you are sharing your screen.

Q2 - ---
The analytics team uses the live dashboard reporting function you built to see how much money is owed in total to all drivers.
Add the following functions:
pay_up_to(pay_time[integer, Unix time from epoch])
Pay all drivers for recorded deliveries which ended up to and including the given time
get_total_cost_unpaid()
Return the total, aggregated cost of all drivers' deliveries which have not been paid

The solution does not need to be thread-safe or handle concurrency.

Q3 - ---
Now we want to get the peak concurrent drivers in the last 24 hours from a given point in time. Implement a function that does that.
"""

from dataclasses import dataclass
from typing import Dict, List
from decimal import Decimal, ROUND_HALF_UP
import time
import uuid

@dataclass(frozen=True)
class Delivery:
    """Represents a single delivery with immutable properties."""
    delivery_id: str      # Unique identifier for this delivery
    driver_id: int
    start_time: int  # Unix timestamp
    end_time: int    # Unix timestamp
    cost: Decimal    # Calculated cost in USD
    
    def __post_init__(self):
        """Validate delivery data after initialization."""
        if self.start_time >= self.end_time:
            raise ValueError("Start time must be before end time")
        
        duration_hours = (self.end_time - self.start_time) / 3600
        if duration_hours > 3:
            raise ValueError("Delivery cannot exceed 3 hours")


# Only needed for Q2
@dataclass
class Payment:
    """Represents a payment transaction."""
    payment_id: str          # Unique payment identifier
    driver_id: int
    delivery_ids: List[str]  # IDs of deliveries being paid
    pay_time: int            # Unix timestamp when payment was made
    total_amount: Decimal    # Total amount paid


class Driver:
    """Represents a delivery driver with their deliveries and payment history."""
    
    def __init__(self, driver_id: int, hourly_rate: Decimal):
        self.driver_id = driver_id
        self.hourly_rate = hourly_rate
        self.deliveries: List[Delivery] = []
        # Only needed for Q2
        self.payments: List[Payment] = []
    
    def add_delivery(self, delivery_id: str, start_time: int, end_time: int) -> str:
        """Add a new delivery and return its ID."""
        # Calculate cost based on duration and hourly rate
        duration_seconds = end_time - start_time
        duration_hours = Decimal(str(duration_seconds)) / Decimal('3600')
        cost = (self.hourly_rate * duration_hours).quantize(
            Decimal('0.01'), rounding=ROUND_HALF_UP
        )
        
        delivery = Delivery(
            delivery_id=delivery_id,
            driver_id=self.driver_id,
            start_time=start_time,
            end_time=end_time,
            cost=cost
        )
        
        self.deliveries.append(delivery)
        return delivery_id
    
    def get_total_cost(self) -> Decimal:
        """Get total cost of all deliveries."""
        return sum(delivery.cost for delivery in self.deliveries)
    
    # Only needed for Q2
    def _get_unpaid_deliveries(self, cutoff_time: int) -> List[Delivery]:
        """Get deliveries that haven't been paid up to the cutoff time."""
        # Get all delivery IDs that have been paid (regardless of payment time)
        paid_delivery_ids = set()
        for payment in self.payments:
            paid_delivery_ids.update(payment.delivery_ids)
        
        # Return deliveries that ended up to cutoff time and haven't been paid
        return [
            delivery for delivery in self.deliveries
            if delivery.delivery_id not in paid_delivery_ids and delivery.end_time <= cutoff_time
        ]
    
    # Only needed for Q2
    def get_unpaid_cost(self, cutoff_time: int) -> Decimal:
        """Get total cost of unpaid deliveries up to cutoff time."""
        unpaid_deliveries = self._get_unpaid_deliveries(cutoff_time)
        return sum(delivery.cost for delivery in unpaid_deliveries)
    
    # Only needed for Q2
    def pay_deliveries(self, cutoff_time: int, pay_time: int) -> Payment:
        """Pay for all unpaid deliveries up to cutoff time."""
        unpaid_deliveries = self._get_unpaid_deliveries(cutoff_time)
        if not unpaid_deliveries:
            return None
        
        # Get delivery IDs (indices in the deliveries list)
        delivery_ids = [
            delivery.delivery_id for delivery in unpaid_deliveries
        ]
        
        total_amount = sum(delivery.cost for delivery in unpaid_deliveries)
        
        payment = Payment(
            payment_id=str(uuid.uuid4()),  # Generate unique payment ID
            driver_id=self.driver_id,
            delivery_ids=delivery_ids,
            pay_time=pay_time,
            total_amount=total_amount
        )
        
        self.payments.append(payment)
        return payment


class DeliveryDashboard:
    """Main system class for managing delivery drivers and payments."""
    
    def __init__(self):
        self.drivers: Dict[int, Driver] = {}
    
    def add_driver(self, driver_id: int, usd_hourly_rate: Decimal) -> None:
        """Add a new driver to the system."""
        if driver_id in self.drivers:
            raise ValueError(f"Driver {driver_id} already exists")
        
        self.drivers[driver_id] = Driver(driver_id, usd_hourly_rate)
    
    def record_delivery(self, driver_id: int, start_time: int, end_time: int) -> str:
        """Record a delivery for a driver and return delivery ID."""
        if driver_id not in self.drivers:
            raise ValueError(f"Driver {driver_id} not found")
        
        # Generate a unique delivery ID using UUID
        delivery_id = str(uuid.uuid4())
        return self.drivers[driver_id].add_delivery(delivery_id, start_time, end_time)
    
    def get_total_cost(self) -> Decimal:
        """Get total cost of all deliveries across all drivers."""
        total = sum(driver.get_total_cost() for driver in self.drivers.values())
        return total
    
    # Only needed for Q2
    def pay_up_to(self, pay_time: int) -> List[Payment]:
        """Pay all drivers for deliveries completed up to the given time."""
        payments = []
        for driver in self.drivers.values():
            payment = driver.pay_deliveries(pay_time, pay_time)
            if payment:
                payments.append(payment)
        return payments
    
    # Only needed for Q2
    def get_total_cost_unpaid(self) -> Decimal:
        """Get total cost of unpaid deliveries across all drivers."""
        # Use current time as cutoff for unpaid deliveries
        current_time = int(time.time())
        total = sum(
            driver.get_unpaid_cost(current_time) 
            for driver in self.drivers.values()
        )
        return total

    # Only needed for Q3
    def _get_all_deliveries_in_past_24_hours_from_time(self, time: int) -> List[Delivery]:
        """Get all deliveries in the past 24 hours from the given time."""
        return [
            delivery for driver in self.drivers.values()
            for delivery in driver.deliveries
            if delivery.end_time >= time - 86400 and delivery.end_time <= time
        ]

    # Only needed for Q3
    """
    Assuming each driver is doing only one delivery at any given time.
    Thus the max concurrent drivers is the max number of deliveries at any given time.
    """
    def get_peak_concurrent_drivers(self, time: int) -> int:
        """Get the peak concurrent driver count in the past 24 hours from the given time."""
        deliveries = self._get_all_deliveries_in_past_24_hours_from_time(time)
        event_list = []
        for delivery in deliveries: 
            event_list.append((delivery.start_time, 0))
            event_list.append((delivery.end_time, 1))
        event_list.sort(key=lambda event: event[0])
        cnt, max_cnt = 0, 0
        for event in event_list:
            if event[1] == 0:
                cnt += 1
                max_cnt = max(max_cnt, cnt)
            else:
                cnt -= 1
        return max_cnt


# Example usage and testing
if __name__ == "__main__":
    # Create dashboard
    dashboard = DeliveryDashboard()
    
    # Add drivers
    dashboard.add_driver(1, Decimal('10.00'))  # $10/hour
    dashboard.add_driver(2, Decimal('15.00'))  # $15/hour
    
    # Record deliveries
    current_time = int(time.time())
    
    # Driver 1: 1.5 hour delivery = $15.00
    delivery1_id = dashboard.record_delivery(
        driver_id=1,
        start_time=current_time - 5400,  # 1.5 hours ago
        end_time=current_time
    )
    
    # Driver 2: 1 hour delivery = $15.00
    delivery2_id = dashboard.record_delivery(
        driver_id=2,
        start_time=current_time - 7200,  # 2 hours ago
        end_time=current_time - 3600     # 1 hour ago
    )
    
    # Get total cost
    total_cost = dashboard.get_total_cost()
    print(f"Total cost: ${total_cost}")  # Should be $30.00
    
    # Q2: Check Payment Due
    unpaid_cost = dashboard.get_total_cost_unpaid()
    print(f"Unpaid cost: ${unpaid_cost}")  # Should be $30.00
    
    # Pay for deliveries
    payments = dashboard.pay_up_to(current_time)
    print(f"Made {len(payments)} payments")
    
    # Check unpaid cost after payment
    unpaid_after = dashboard.get_total_cost_unpaid()
    print(f"Unpaid cost after payment: ${unpaid_after}")  # Should be $0.00

    max_cnt = dashboard.get_peak_concurrent_drivers(current_time)
    print(f"Max concurrent drivers: {max_cnt}")  # Should be 2
