Api:

For add a contact:

Send a post method with params "action = addContact" and body with xml.
Example:
localhost:8080/xml?action=editContact

```<?xml version="1.0" encoding="UTF-8"?>
<contact>
    <lastName>Tarb</lastName>
    <firstName>Cermu</firstName>
</contact>
```
