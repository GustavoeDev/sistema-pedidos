from orders.models import Order, OrderItem
from rest_framework import serializers

class OrderItemSerializer(serializers.ModelSerializer):
    class Meta:
        model = OrderItem
        fields = ['id', 'product_id', 'quantity']

class OrderSerializer(serializers.ModelSerializer):
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
            
        # implementar a lógica de validação de existencia de cliente e produtos

        return data

    def create(self, validated_data):
        items_data = validated_data.pop('items')
        
        order = Order.objects.create(**validated_data)
        
        for item in items_data:
            OrderItem.objects.create(order=order, **item)
            
        return order