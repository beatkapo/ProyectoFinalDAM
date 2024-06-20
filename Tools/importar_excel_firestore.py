import tkinter as tk
from tkinter import filedialog
import pandas as pd
from google.cloud import firestore
import os

def import_data_from_excel(file_path):
    try:
        # Leer el archivo de Excel
        xls = pd.ExcelFile(file_path)
        collections = xls.sheet_names

        # Conectar con Firestore
        db = firestore.Client()

        # Procesar cada hoja (colección)
        for collection_name in collections:
            # Leer los datos de la hoja
            df = pd.read_excel(xls, collection_name)

            # Obtener los nombres de los campos
            fields = df.columns.tolist()

            # Convertir los datos a formato adecuado para Firestore
            data = df.to_dict(orient='records')

            # Guardar los datos en Firestore
            doc_ref = db.collection(collection_name)
            for doc_data in data:
                # Crear un diccionario para el documento
                doc_dict = {}
                for field in fields:
                    doc_dict[field] = doc_data[field]

                # Agregar el documento a la colección
                doc_ref.add(doc_dict)

        return 'Datos importados exitosamente a Firestore.'
    except Exception as e:
        return f'Error al importar datos: {str(e)}'

def browse_file():
    file_path = filedialog.askopenfilename(filetypes=[("Excel files", "*.xlsx;*.xls")])
    if file_path:
        result_label.config(text="Importando datos...")
        result = import_data_from_excel(file_path)
        result_label.config(text=result)
        root.after(3000, root.destroy)  # Cerrar la ventana después de 3 segundos

# Configurar la ubicación del archivo de credenciales
os.environ["GOOGLE_APPLICATION_CREDENTIALS"] = "C:/Users/Besugo/Documents/Clase/AppCabanes/gestion-de-pedidos-63648-firebase-adminsdk-vv02y-60db5d82aa.json"

# Crear la interfaz de usuario
root = tk.Tk()
root.title("Importar datos desde Excel")

# Botón para seleccionar el archivo
browse_button = tk.Button(root, text="Seleccionar archivo", command=browse_file)
browse_button.pack(pady=20)

# Etiqueta para mostrar el resultado de la importación
result_label = tk.Label(root, text="", fg="green")
result_label.pack(pady=10)

root.mainloop()

