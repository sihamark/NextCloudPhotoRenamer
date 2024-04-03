# Next Cloud Photo Renamer

This is a simple script that renames photos in a Next Cloud directory to a format that is more human
readable.
The script will rename the photos to the format `YYYY-MM-DD HH.MM.SS.jpg`(TBD) based on the date the
photo was taken.

## Todos

- template functions:
    - in the end all picture of me and everyone else should be in one folder with the same format
    - analyse how the author might change when renaming file with this bot
    - analyse file templates for every used format
    - decide on a target template
    - implement ui to select file for template (what file is allegible for what template)
    - add progress for renaming function
    - add function for view images
    - add function to move pictures to a new folder
- diary function:
    - find a way to write diary entries
    - find a way to add pictures to diary entries
        - select by date and specific tag
        - use a keyword for a caption
    - compile text and images into a human readable and printable format (maybe using pdfbox)
- image functions
    - show me pictures from (1/2/3) years ago