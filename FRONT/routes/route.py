from flask import Blueprint, abort, request, render_template, redirect
import json
import requests
from flask import flash
from flask import Blueprint, jsonify


router = Blueprint ('router', __name__)

@router.route('/')
def home():

    return render_template('fragmento/inicial/login.html')

@router.route('/admin')
def admin():
    return render_template('fragmento/inicial/admin.html')

@router.route('/admin/validador/register')
def view_register_validador():
    r_validador = requests.get("http://localhost:8086/api/validador/list")
    data_validador = r_validador.json()

    return render_template('fragmento/validador/registro.html', lista_validador=data_validador["data"])

@router.route('/admin/validador/list')
def list_validador(msg=''):
    r_validador = requests.get("http://localhost:8086/api/validador/list")
    data_validador = r_validador.json()
    print(data_validador)
    
    return render_template('fragmento/validador/lista.html', lista_validador=data_validador["data"])


@router.route('/admin/validador/save', methods=['POST'])
def save_validador():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    data_validador = { 
        "htmlCode": form["htmlCode"],
    }

    r_validador = requests.post("http://localhost:8086/api/validador/save", data=json.dumps(data_validador), headers=headers)     # Hacer la petición para guardar la validador


    if r_validador.status_code == 200:

        flash("Registro guardado correctamente", category='info')
        return redirect('/admin/validador/list')
    else:
        flash(r_validador.json().get("data", "Error al guardar la validador"), category='error')
        return redirect('/admin/validador/list')
    

    
@router.route('/admin/validador/delete/<int:id>', methods=['POST'])
def delete_validador(id):
   
    requests.delete(f"http://localhost:8086/api/validador/delete/{id}")  # Solicitar la eliminación de la validador y el generador asociado

    return redirect('/admin/validador/list')    # Redirigimos al usuario a la lista de validadores




# @router.route('/admin/validador/edit/<id>', methods=['GET'])
# def view_edit_person(id):

#     r = requests.get("http://localhost:8086/api/validador/listType")
#     lista_tipos = r.json()  # Guardamos la respuesta JSON


#     r1 = requests.get(f"http://localhost:8086/api/validador/get/{id}")     # Obtenemos los datos de la validador por ID

#     if r1.status_code == 200:
#         data_validador = r1.json()

#         if "data" in data_validador and data_validador["data"]:      # Verificamos que la respuesta contenga datos válidos
#             validador = data_validador["data"]


#             if validador["tieneGenerador"]:     # Obtenemos los datos del generador asociado a la validador
#                 r_generador = requests.get(f"http://localhost:8086/api/generador/get/{validador['id']}")
#                 if r_generador.status_code == 200:
#                     data_generador = r_generador.json()
#                     generador = data_generador["data"] if "data" in data_generador else None
#                 else:
#                     generador = None  # Si no se encontraron datos del generador, se inicializa a None

#             else:
#                 generador = None # Si la validador no tiene generador, se inicializa a None

#             return render_template('fragmento/validador/editar.html', lista=lista_tipos["data"], validador=validador, generador=generador)
#         else:
#             flash("No se encontraron datos para la validador.", category='error')
#             return redirect("/admin/validador/list")
#     else:
#         flash("Error al obtener la validador", category='error')
#         return redirect("/admin/validador/list")
    

# @router.route('/admin/validador/update', methods=['POST'])
# def update_person():
#     headers = {'Content-Type': 'application/json'}
#     form = request.form

#     data_validador = {
#         "id": form["id"],
#         "canton": form["can"],
#         "apellidoPaterno": form["ape"],
#         "apellidoMaterno": form["apem"],
#         "integrantes": form["inte"],
#         "tieneGenerador": form["tieneg"] == 'true'
#     }

#     if form["tieneg"] == 'true':  # Solo si tiene generador
#         data_generador = {
#             "id": form["id"],  # Usamos el mismo ID que la validador
#             "costo": form["cost"],
#             "consumoXHora": form["conxh"],
#             "energiaGenerada": form["energen"],
#             "uso": form["uso"],
#         }
#     else:

#         data_generador = {  # Inicializa el generador a valores predeterminados
#             "id": form["id"],  
#             "costo": 0,  
#             "consumoXHora": 0,  
#             "energiaGenerada": 0,  
#             "uso": 'ninguno',  
#         }

#     r_generador = requests.post("http://localhost:8086/api/generador/update", data=json.dumps(data_generador), headers=headers)

#     if r_generador.status_code != 200:
#         flash("Error al actualizar el generador: " + r_generador.json().get("data", ""), category='error')
   
#     r_validador = requests.post("http://localhost:8086/api/validador/update", data=json.dumps(data_validador), headers=headers)
    
#     if r_validador.status_code == 200:
#         flash("Registro de validador guardado correctamente", category='info')
#         return redirect('/admin/validador/list')
#     else:
#         flash(r_validador.json().get("data", "Error al actualizar la validador"), category='error')
#         return redirect('/admin/validador/list')



def load_data(file_path):
    with open(file_path, 'r') as file:
        return json.load(file)




