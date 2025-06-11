import requests

from rest_framework import serializers

def customer_exists(customer_id):
    url = f"http://costumersservice:8000/api/v1/costumers/{customer_id}/"

    try:
        response = requests.get(url)
        response.raise_for_status()

        dados = response.json()

        return dados

    except requests.RequestException as e:
        print(f"Erro ao chamar o serviço de clientes: {e}")
        return None


def check_products_existence(items_list):
    """
    Chama o serviço de produtos para verificar a existência de uma lista de itens.
    Levanta um ValidationError em caso de falha.
    """
    url = "http://productservice:8080/products/existence-check"

    # --- INÍCIO DA CORREÇÃO ---
    # ANTES de enviar, precisamos garantir que todos os UUIDs sejam strings.
    # A lista `items_list` que recebemos do DRF contém objetos UUID.
    serializable_items = [
        {
            "product_id": str(item.get('product_id')),  # AQUI ESTÁ A MUDANÇA
            "quantity": item.get('quantity')
        }
        for item in items_list
    ]
    # --------------------------

    # O payload JSON deve ter a chave "items" contendo a lista JÁ SERIALIZÁVEL
    payload = {"items": serializable_items}

    try:
        response = requests.post(url, json=payload, timeout=5)
        response.raise_for_status()
        return response.json()

    except requests.exceptions.HTTPError as e:
        if e.response.status_code == 404:
            raise serializers.ValidationError(e.response.text)
        else:
            raise serializers.ValidationError(f"Ocorreu um erro ao verificar os produtos: {e.response.status_code}")

    except requests.exceptions.RequestException as e:
        raise serializers.ValidationError(f"Não foi possível conectar ao serviço de produtos: {e}")
