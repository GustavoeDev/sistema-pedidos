from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from django.shortcuts import get_object_or_404

from orders.models import Order, OrderItem
from orders.api.v1.serializers import OrderSerializer, OrderItemSerializer

class OrderAPIView(APIView):
    def get(self, request):
        orders = get_object_or_404(Order.objects.all())
        serializer = OrderSerializer(orders, many=True)
        return Response(serializer.data)

    def post(self, request):
        serializer = OrderSerializer(data=request.data)
        if serializer.is_valid():
            order = serializer.save()
            return Response(OrderSerializer(order).data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)