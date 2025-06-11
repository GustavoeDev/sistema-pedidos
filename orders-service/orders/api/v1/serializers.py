from orders.models import Order, OrderItem
from rest_framework import serializers

from orders.api.requests import customer_exists, check_products_existence

class OrderItemSerializer(serializers.ModelSerializer):
    class Meta:
        model = OrderItem
        fields = ['product_id', 'quantity']

class OrderSerializer(serializers.ModelSerializer):
    items = OrderItemSerializer(many=True, read_only=True)

    class Meta:
        model = Order
        fields = ['id', 'customer_id', 'items']
        read_only_fields = ['id']

class OrderCreateSerializer(serializers.ModelSerializer):
    items = OrderItemSerializer(many=True)

    class Meta:
        model = Order
        fields = ['id', 'customer_id', 'items']
        read_only_fields = ['id']

    def validate(self, data):
        items = data.get('items', [])
        costumer_id = data.get('customer_id')
        if not items:
            raise serializers.ValidationError("At least one order item is required.")
        if not costumer_id:
            raise serializers.ValidationError("Customer ID is required.")

        customer = customer_exists(customer_id=costumer_id)
        print(customer)
        if not customer:
            raise serializers.ValidationError("Customer not found in the customer service.")

        check_products_existence(items)

        return data

    def create(self, validated_data):
        items_data = validated_data.pop('items')
        
        order = Order.objects.create(**validated_data)
        
        for item in items_data:
            OrderItem.objects.create(order=order, **item)
            
        return order