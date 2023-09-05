"""news URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/4.1/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path, include
from rest_framework.schemas import get_schema_view
from rest_framework.documentation import include_docs_urls
from rest_framework_swagger.views import get_swagger_view
from . import views

DOCS_TITLE = "News docs"
DOCS_DESCRIPTION = "v0"

urlpatterns = [
    path('', views.index, name='index'),
    path('admin/', admin.site.urls),
    path('api/v0/news/irna/', include('rss_to_json.urls')),
    path('schema/', get_schema_view(title="News schema", description="v0")),
    path('docs/', include_docs_urls(DOCS_TITLE, DOCS_DESCRIPTION)),
    path('swagger-ui/', get_swagger_view(DOCS_TITLE)),
    path('api-auth/', include('rest_framework.urls')),
]
