from rest_framework.serializers import ModelSerializer

from costumers.models import Customer


class CustomerSerializer(ModelSerializer):
    class Meta:
        model = Customer
        fields = ['id', 'name', 'email']