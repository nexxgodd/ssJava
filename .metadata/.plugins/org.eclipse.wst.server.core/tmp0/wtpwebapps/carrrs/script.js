fillShowRoom();


function doAnAjax(method,url,callback){
	var xhr = new XMLHttpRequest();
	xhr.onload = function() {
		callback(xhr);
	};
	xhr.open(method,url);
	//xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
	xhr.send(null);
}

getByVin("jhmge8G24AC915549");
function getByVin(vin){
	//e.preventDefault();
	//jhmge8G24AC915549
	doAnAjax('GET', 'http://localhost:8080/carrrs/api/rental?vin='+vin,function({responseText}){
			console.log('Success') // log framework
			var car = JSON.parse(responseText);
			let out=carBuilder(car);
			console.log(out);
			//document.getElementById("showroom").insertAdjacentHTML( 'beforeend',out);
	});
}


function fillShowRoom(){
	doAnAjax('GET', 'http://localhost:8080/carrrs/api/rental',function({responseText}){
			//console.log('Success') // log framework
			var cars = JSON.parse(responseText);
			//console.log(cars);

			for(car of cars){
				
			document.getElementById("showroom").insertAdjacentHTML( 'afterbegin',carBuilder(car));
			}
			//addTraineeRecordToTable(car);
	});
	//showroom
}

// function deleteByVin(vin){
// 	//e.preventDefault();
// 	//jhmge8G24AC915549
// 	doAnAjax('DELETE', 'http://localhost:8080/carrrs/api/rental',function({responseText}){
// 			console.log('Success') // log framework
// 			// var car = JSON.parse(responseText);
// 			//console.log(out);

// 			// let out=carBuilder(car);
// 			//document.getElementById("showroom").insertAdjacentHTML( 'beforeend',out);
// 	});
// }

function deleteByVin(vin){
	let xhr = new XMLHttpRequest();
	// 2. define what happens during the Ajax call
	// handler / callback function
	xhr.onreadystatechange = function(){
		if(xhr.readyState === 4){
			// //console.log(xhr.responseText)
			// var traineeArray = JSON.parse(xhr.responseText)
			// console.log(traineeArray)
			// // dom manipulation codes
			// traineeArray.forEach(trainee => {
			// 	addTraineeRecordToTable(trainee)
			// });
		}
	}
	// 3. open the xhr call (HTTP verb, and the URL)
	// url with beginning / 'context root'
	xhr.open('DELETE', 'http://localhost:8080/carrrs/api/rental');
	// 4. send the ajax call to the server
xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
	xhr.send();
}







function urlBuilder(url){
	const alamo="https://assets.gcs.ehi.com/content/alamo/data/vehicle/bookingCountries/US/CARS/ICAR.doi.200.high.imageSmallThreeQuarterNodePath.png/";
	if(true||!url){
		return "resources/noimg.png"
	}

}

function carBuilder(c){
	return `
<div id="${c.vin}" class="car">
	<div class="col card" tag="${c.body_style} ${c.color}">
		<div class="card-body">
			<div class="text-end">
				<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
				<path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
				<path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
				</svg>
			</div>
			<div><img src='${urlBuilder(c.url)}' class="display-img"></div>
			<div>${c.year} ${c.make} ${c.model}</div>
			<div>${c.vin}</div>
			<div><strong>$${c.price}</strong>/day</div>
			<button class="btn btn-primary" onclick="deleteByVin('${c.vin}')">Set Sail!</button>
		</div>
	</div>
</div>`
}