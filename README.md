# doc-trans
A program that utilize google api to translate word document. The program will translate the document paragraph by paragraph, 
and then put the translated text below the original paragraph. Finally, it will rewrite the document with the new text. 
It currently support doc and docx format, from chinese to english.

### Setup Requirements
- The program requires a private key JSON file provided by google cloud.
- Before running the program, make sure to set the environment variable GOOGLE_APPLICATION_CREDENTIALS to the file path of the JSON file.
