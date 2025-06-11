from gc import get_objects

from django.shortcuts import get_object_or_404
from rest_framework import status
from rest_framework.response import Response
from rest_framework.views import APIView

from costumers.api.v1.serializers import CustomerSerializer
from costumers.models import Customer


class CustomersAPIView(APIView):
    def get(self, request):
        customers = Customer.objects.all()
        serializer = CustomerSerializer(customers, many=True)

        if customers:
            return Response(serializer.data, status=status.HTTP_200_OK)
        else:
            return Response(data='None costumers found', status=status.HTTP_404_NOT_FOUND)

    def post(self, request):
        serializer = CustomerSerializer(data=request.data)

        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)

        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class CustomerExistsAPIView(APIView):
    def get(self, request, id):
        customer = get_object_or_404(Customer, id=id)

        serializer = CustomerSerializer(customer)
        if customer:
            return Response(serializer.data, status=status.HTTP_200_OK)
        else:
            pass