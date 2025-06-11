from django.db import models

# Create your models here.

class Customer(models.Model):
    name = models.CharField(max_length=100, blank=False, null=False)
    email = models.EmailField(blank=False, null=False, unique=True)