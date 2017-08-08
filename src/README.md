# jStegoLSB
JStegoLSB is a program that allows you to hide data in image file containers.
It uses two bits of red, green, blue colors of each pixel. Alpha-channel is not used.
Supported containers formats are PNG, BMP, JPEG. However the image with encrypted data will
be saved to PNG format.

You can encrypt file or just a text. To start:
1. Choose a container in Open menu.
2. Choose a file to insert into image in Open menu or type message you want to insert.
3. Press "Insert file" button or "Insert text" button respectively.
4. Processed image will be saved to the original image parent folder.

To obtain data from container:
1. Choose a container in Open menu.
2. Press "Extract file" button or "Extract text" button respectively.
3. File will be saved to the container image parent folder.
