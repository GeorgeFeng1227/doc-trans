# doc-trans
A program that utilizes Google’s cloud API to translate word documents. It has a GUI created with Java Swing library.


### Modes
##### Document Only Mode
Under this mode, the program only accepts one document at a time. The user can choose the file from a file chooser window, which will filter out those documents that are not supported by the program. And then, the user should choose "Rewrite original file", because the program does not support automatically creating new word document yet. However, the user may create a empty word document manually, and choose it as the output path. (Notice: the file type must match the input file type) During the translating process, the user can view the progress from the progress bar.

##### Folder Mode
Under this mode, the program can translate a single folder at a time. It will automatically skip those files that are not supported by the program. The original files will be rewritten with updated content. During the translating process, there will be two progress bars: one shows the progress of translating a single document, and the other shows the overall progress.


### Language and File Format Supported
From **Simplified Chinese** to **English**.
Supports **Doc** and **Docx**.


### Output Mode
##### Alternating mode
The program will translate the document paragraph by paragraph, and then put the translated text below the original paragraph. Thus, the final text will alternate between two languages, which is convenient for comparison. 

##### Translated after Original Text
The Original text will keep as it’s original form and the translated text will be added to the end.

##### Keep Translated Text Only
As stated by the name.


### Setup Requirements
- The program requires a private key JSON file provided by google cloud.
- Before running the program, make sure to set the environment variable GOOGLE_APPLICATION_CREDENTIALS to the file path of the JSON file. (the environment setting method is located in the Frame1 class.)


