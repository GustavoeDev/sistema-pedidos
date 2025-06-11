from django.db import models

# Create your models here.
class Order(models.Model):
    customer_id = models.IntegerField()

    def __str__(self):
        return f"Order {self.id} for Customer {self.customer_id}"


class OrderItem(models.Model):
    order = models.ForeignKey(Order, on_delete=models.CASCADE, related_name='items')
    product_id = models.UUIDField()
    quantity = models.IntegerField()

    def __str__(self):
        return f"Order Item {self.id} from Order {self.order.id} - Product {self.product_id} (Quantity: {self.quantity})"