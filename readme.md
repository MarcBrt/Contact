Login/Register:

To register go to localhost:8080/register.
To login, just go to localhost.

Api:

For add a contact:

Send a post method with params "action = addContact" and body with xml.
Example:
localhost:8080/xml?action=addContact

```<?xml version="1.0" encoding="UTF-8"?>
<contact>
    <lastName>Tarb</lastName>
    <firstName>Cermu</firstName>
</contact>
```

For edit a contact:

Send a post method with params "action = editContact", "id = <id of contact>" and body with xml.
Example:
localhost:8080/xml?action=editContact&id=225

```<?xml version="1.0" encoding="UTF-8"?>
<contact>
    <lastName>Marc</lastName>
    <firstName>Bertu</firstName>
</contact>
```


