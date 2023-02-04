# python -m pip install suds
from suds.client import Client
c = Client('http://localhost:8000/?wsdl')

"""
isi extrage metodele de care are nevoie in WSDL
GET e permisa pentru obtinerea WSDL-ului
POST - interactiunea cu metodele 
"""

# print(c.service.addition("asdf", "ghjkl"))  # da eroare
print(c.service.addition(10, 5))
print(c.service.substraction(10, 5))
