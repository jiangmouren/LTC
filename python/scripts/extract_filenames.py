import os
import sys
import re

def extract_number_from_filename(filename):
    """
    Extract the number from the beginning of a filename.
    Returns the number if found, otherwise returns a large number for sorting.
    
    Args:
        filename (str): The filename to extract number from
        
    Returns:
        int: The extracted number or a large number for sorting
    """
    # Look for pattern like "1)", "10)", "100)" at the beginning
    match = re.match(r'^(\d+)\)', filename)
    if match:
        return int(match.group(1))
    else:
        # If no number found, return a large number to sort at the end
        return 999999

def extract_filenames(directory_path, output_file):
    """
    Extract all file names from the specified directory and save them to a file.
    
    Args:
        directory_path (str): Path to the directory to scan
        output_file (str): Path to the output file where filenames will be saved
    """
    try:
        # Check if directory exists
        if not os.path.exists(directory_path):
            print(f"Error: Directory '{directory_path}' does not exist.")
            return False
        
        if not os.path.isdir(directory_path):
            print(f"Error: '{directory_path}' is not a directory.")
            return False
        
        # Get all files in the directory and subdirectories
        all_files = []
        
        for root, dirs, files in os.walk(directory_path):
            for file in files:
                # Get the full path of the file
                full_path = os.path.join(root, file)
                # Get relative path from the base directory
                relative_path = os.path.relpath(full_path, directory_path)
                all_files.append(relative_path)
        
        # Sort the files by numeric value at the beginning of filename
        all_files.sort(key=extract_number_from_filename)
        
        # Write filenames to output file
        with open(output_file, 'w', encoding='utf-8') as f:
            for filename in all_files:
                f.write(filename + '\n')
        
        print(f"Successfully extracted {len(all_files)} file names to '{output_file}'")
        return True
        
    except PermissionError:
        print(f"Error: Permission denied accessing directory '{directory_path}'")
        return False
    except Exception as e:
        print(f"Error: {str(e)}")
        return False

def main():
    # Directory to scan
    directory_path = r"G:\My Drive\Zhou v. Jiang\Trial\ER 904\25-08-15 Petitioner's ER 904s"
    
    # Output file name
    output_file = "extracted_filenames.txt"
    
    print(f"Scanning directory: {directory_path}")
    print(f"Output file: {output_file}")
    print("-" * 50)
    
    # Extract filenames
    success = extract_filenames(directory_path, output_file)
    
    if success:
        print(f"\nFile names have been saved to '{output_file}'")
        print("You can open this file to view all the extracted file names.")
    else:
        print("\nFailed to extract file names.")

if __name__ == "__main__":
    main()
