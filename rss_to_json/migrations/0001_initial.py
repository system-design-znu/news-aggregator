# Generated by Django 4.2.6 on 2023-10-09 15:30

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Archive',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('title', models.CharField(blank=True, max_length=126, null=True)),
                ('author', models.CharField(blank=True, max_length=126, null=True)),
                ('publish_date', models.CharField(blank=True, max_length=126, null=True, verbose_name='Publish date (as string)')),
            ],
        ),
    ]
