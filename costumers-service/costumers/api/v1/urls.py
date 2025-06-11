from django.urls import path
from .views import *

urlpatterns = [
    path('costumers', CustomersAPIView.as_view()),
    path('costumers/<int:id>/', CustomerExistsAPIView.as_view()),
]