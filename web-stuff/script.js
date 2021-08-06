fillShowRoom();


var formatter = new Intl.NumberFormat('en-US', {
	style: 'currency',
	currency: 'USD',
});
  
//   formatter.format(2500); /* $2,500.00 */


function doAnAjax(method,url,callback,bod){
	const path ='http://localhost:8080/carrrs/api/rental';
	var xhr = new XMLHttpRequest();
	xhr.onload = function() {
		//console.log(xhr);
		callback(xhr.status, xhr.responseText);
	};
	console.log(path+url)
	xhr.open(method,path+url);
	xhr.send(bod);
	
}

// //getByVin("jhmge8G24AC915549");
// function getByVin(vin){
// 	//e.preventDefault();
// 	//jhmge8G24AC915549
// 	doAnAjax('GET', '?vin='+vin,function(response,status){
// 			//console.log('Success') // log framework
// 			var car = JSON.parse(response);
// 			let out=carBuilder(car);
// 			//console.log(out);
// 			//document.getElementById("showroom").insertAdjacentHTML( 'beforeend',out);
// 	});
// }


function fillShowRoom(){
	doAnAjax('GET', '',function(status, response){
		if(status===200){
			var cars = JSON.parse(response);
			for(car of cars){
				document.getElementById("showroom").insertAdjacentHTML( 'afterbegin',carBuilder(car));
			}
		}
		else{
			console.log("Failed to get data")
		}
	});
	//showroom
}

function createNewCar(car){
	doAnAjax('POST', '',function(status){
		if(status===201){
			document.getElementById("notacar").insertAdjacentHTML( 'beforebegin',carBuilder(car));
			newCarModal.hide();
		}
		else{
			alert("Failed to create!");
		}
	},JSON.stringify(car));
}

function deleteByVin(vin){
	doAnAjax('DELETE', '?vin='+vin,function(status){
		if(status===200){
			document.getElementById(vin).remove();
		}
		else{
			alert("Failed to delete!");
		}
	});
}

function editByVin(vin,car){
	doAnAjax('PUT', '?vin='+vin,function(status){
		console.log(car);
		if(status===200){
			let ele =document.getElementById(vin);
			ele.insertAdjacentHTML("beforebegin",carBuilder(car));
			ele.remove();
			newCarModal.hide();
		}
		else{
			alert("Failed to edit!");
		}
	},JSON.stringify(car));
}


function urlBuilder(url){
	return !url?"resources/noimg.png":url;
}



const newCarModal = new bootstrap.Modal(document.getElementById('newCar'), {
	keyboard: true
});
function openEditor(vin) {
	currentVin=vin;

	let car =document.getElementById(vin);

	document.getElementById("newCarLabel").innerText="Edit this vehicle";
	document.getElementById("inputVIN").value=vin;
	document.getElementById("inputYear").value=car.getAttribute("year");
	document.getElementById("inputMake").value=car.getAttribute("make");
	document.getElementById("inputModel").value=car.getAttribute("model");
	document.getElementById("inputBody").value=car.getAttribute("body");
	document.getElementById("inputColor").value=car.getAttribute("color");
	document.getElementById("inputUrl").value=car.getAttribute("image");
	document.getElementById("inputPrice").value=car.getAttribute("price");

	document.getElementById("newCarLabel").innerText="Edit this vehicle - "+vin;
	document.getElementById("modalButton").innerText="Update";
	document.getElementById("newCarForm").onsubmit=doModalEdit;
	
	newCarModal.show();
}

function openCreator(){
	document.getElementById("newCarForm").reset();

	document.getElementById("newCarLabel").innerText="Create new vehicle";
	document.getElementById("modalButton").innerText="Create";
	document.getElementById("newCarForm").onsubmit=doModalCreate;

	newCarModal.show();
}

function carBuilder(c){
	//let price=;
	return `
<div id="${c.vin}" class="col car " year="${c.year}" make="${c.make}" model="${c.model}" body="${c.body_style}" color="${c.color?c.color:""}" image="${c.url}" price="${c.price}">
	
	<div class="card small-card">
		<div class="card-body d-flex align-items-end flex-column">
			<div class="text-end mb-auto">
				<button class="edit-btn" onclick="openEditor('${c.vin}')">
				<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi light-text bi-pencil-square" viewBox="0 0 16 16">
				<path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
				<path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
				</svg>
				</button>
			</div>
			<div class="">
				<br>
				<div class="display-img"><img class="img-fluid" src='${urlBuilder(c.url)}' onError="this.onerror=null;this.src='resources/noimg.png';"></div>
				<div>${c.year} ${c.make} ${c.model}</div>
				<div>${c.vin}</div>
				<div><strong>${formatter.format(c.price)}</strong>/day</div>
				<br>
				<button class="btn btn-secondary" onclick="deleteByVin('${c.vin}')">Set Sail!</button>
			</div>
		</div>
	
	</div>
</div>`
}


var currentVin=null;



function doModalEdit(e){
	e.preventDefault();
	var car = getCarFromModal();
	editByVin(currentVin,car);
	// var newCarModal = new bootstrap.Modal(document.getElementById('newCar'), {
	// 	keyboard: true
	// });
	
	
}

function doModalCreate(e){
	e.preventDefault();
	var car = getCarFromModal();
	createNewCar(car);
	// var newCarModal = new bootstrap.Modal(document.getElementById('newCar'), {
	// 	keyboard: true
	// });
	
	
}

function getCarFromModal(){
	let hue=document.getElementById("inputColor").value;
	return{
		"vin": document.getElementById("inputVIN").value,
		"year": document.getElementById("inputYear").value,
		"make": document.getElementById("inputMake").value,
		"model": document.getElementById("inputModel").value,
		"body_style": document.getElementById("inputBody").value,
		"color": hue==="Other"?null:hue,
		"url": document.getElementById("inputUrl").value,
		"price": document.getElementById("inputPrice").value,
		"available": true
	}
}
