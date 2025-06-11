FROM python:3.12

ENV PYTHONDONTWRITEBYTECODE 1
ENV PYTHONUNBUFFERED 1

WORKDIR /code

COPY requirements.txt /code/
RUN pip install --upgrade pip && pip install -r requirements.txt

COPY . /code/

EXPOSE 8001

CMD ["gunicorn", "competitions_service.wsgi:application", "--bind", "0.0.0.0:8001", "--workers", "3", "--timeout", "120"]