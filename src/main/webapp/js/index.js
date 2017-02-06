host = "http://localhost:8080/rest/services/";

function listarCervejas() {
	$.ajax({
		url : host + 'cervejas',
		type : 'GET',
		success : function(data) {
			$('#grid tr:gt(0)').remove();
			if ($.isArray(data.cervejas.link)) {
				for (var i = 0; i < data.cervejas.link.length; i++) {
					var link = data.cervejas.link[i]['@href'];
					seguirLink(link);
				}
			} else {
				var link = data.cervejas.link['@href'];
				seguirLink(link);
			}
		},
		error : function(data) {
			alert("erro na listagem; "+data.status +" "+data.statusText);
		}
	});
}

function seguirLink(link) {
	$.ajax({
		url : host + link,
		type : 'GET',
		success : function(data) {
			adicionarNovaCervejaNaTabela(data.cerveja);
		},
		error : function(data) {
			alert("Erro na tentativa de seguir o link; "+data.status +" "+data.statusText);
		}
	});
}

function adicionarNovaCervejaNaTabela(cerveja){
	var dados = "<tr>" + 
			"<td>"+ cerveja.nome +"</td>" +
			"<td>"+ cerveja.cervejaria +"</td>" +
			"<td>"+ cerveja.descricao +"</td>" +
			"<td>"+ cerveja.tipo +"</td>" +
			"<td><input type=\"button\" value=\"Apagar\" " +
			"onclick=\"apagarCerveja('"+ cerveja.nome +"');\" /></td>" +
			"</tr>";
	$('#grid').append(dados);
}

function adicionarCerveja(){
	var data = $("#criarCervejaForm").serializeJSON();
	data = "{\"cerveja\":"+ JSON.stringify(data) +"}";
	$.ajax({
		url : host + 'cervejas',
		type : 'POST',
		contentType : 'application/json',
		data : data,
		success : function(data) {
			alert("Cerveja incluida com sucesso");
			listarCervejas();
		},
		error : function(data) {
			alert("Erro na inclusão da cerveja; "+data.status +" "+data.statusText);
		}
	});
}

function apagarCerveja(id) {
	$.ajax({
		url : host + 'cervejas/' + id,
		type : 'DELETE',
		success : function(data) {
			listarCervejas();
		},
		error : function() {
			alert("Erro na tentativa de excluir uma cerveja; "+data.status +" "+data.statusText);
		}
	});
}


