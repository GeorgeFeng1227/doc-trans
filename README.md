# doc-trans
A program that utilizes Google’s cloud API to translate word documents. It has a GUI created with Java Swing library.

To try the program, download the files and find the **doc-trans.jar** in the **target** folder, then follow the **Setup Requirements** at the end of this readme. 

### Modes
##### Document Only Mode
Under this mode, the program only accepts one document at a time. The user can choose the file from a file chooser window, which will filter out those documents that are not supported by the program. And then, the user should choose "Rewrite original file", because the program does not support automatically creating new word document yet. However, the user may create an empty word document manually, and choose it as the output path. (Notice: the file type must match the input file type). Output language and mode are also available for selecting. During the translating process, the user can view the progress from the progress bar.

##### Folder Mode
Under this mode, the program can translate a single folder at a time. It will automatically skip those files that are not supported by the program. The original files will be rewritten with updated content. Output language and mode are also available for selecting. During the translating process, there will be two progress bars: one shows the progress of translating a single document, and the other shows the overall progress.


### Language and File Format Supported
The program support **109** different languages, which include all the most widely used languages. (You can try the program to see the full list of the languages or find them at this link https://cloud.google.com/translate/docs/languages). By default, the program will auto-detect the language of the inputting file. User can choose it manually in a pop up if the auto-detect failed

The file formats supported by this program are **Doc** and **Docx**.


### Output Mode
##### Alternating mode
The program will translate the document paragraph by paragraph, and then put the translated text below the original paragraph. Thus, the final text will alternate between two languages, which is convenient for comparison. 

##### Translated after Original Text
The Original text will keep as it’s original form and the translated text will be added to the end.

##### Keep Translated Text Only
As stated by the name.


### Setup Requirements
- The program requires a private key JSON file provided by google cloud.
- Download and save the private key JSON file to your computer. 
- Find the full path to the JSON file because you will need to enter it along with the file name when the program is launched for the first time. (After the first use, when you quit the program with the exit button, this information will be stored in a EnvVa.ser file for the program to use in future launches).




