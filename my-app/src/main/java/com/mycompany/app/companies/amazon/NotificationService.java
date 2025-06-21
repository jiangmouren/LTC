package com.mycompany.app.companies.amazon;
import java.util.*;
enum Type{
    EMAIL,
    TEXT,
    APP;
}

class Notification{
    private final Type type;
    private final String id;
    private final String senderId;
    private final String receiverId;
    private String message;
    public Notification(Type type, String senderId, String receiverId){
        this.type = type;
        this.id = UUID.randomUUID().toString();
        this.senderId = senderId;
        this.receiverId = receiverId;
    }
    public Type getType(){
        return this.type;
    }
}
interface IService{
    public boolean send(Notification notification);
}

class EmailService implements IService{
    public boolean send(Notification notification){
        return true;
    }
}

class TextService implements IService{
    public boolean send(Notification notification){
        return true;
    }
}

class AppService implements IService{
    public boolean send(Notification notification){
        return true;
    }
}

public class NotificationService implements IService {
    private final Map<Type, IService> serviceMap;
    private static final NotificationService instance = new NotificationService();
    private NotificationService(){
        this.serviceMap = new HashMap<>();
        //logic to create different services
    }

    public NotificationService getInstance(){
        return this.instance;
    }

    public boolean send(Notification notification){
        IService service = serviceMap.get(notification.getType());
        return service.send(notification);
    }
}
