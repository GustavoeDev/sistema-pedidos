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