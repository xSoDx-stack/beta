function saveCargo(){
    let offCanvas = $('#staticBackdrop');
    let cargo = {
        id: offCanvas.find("#inputId").val(),
        pecCode: offCanvas.find('#inputPecCode').val(),
        processed: $("#labelProcessed").prop('checked'),
        issuance: $("#labelIssuance").prop('checked')
    }


    $.ajax({
        url: '/api/v1/cargo/save',
        type: 'POST',
        data: JSON.stringify(cargo),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: (cargo) => {
            showingCargoOffCanvas(cargo)
        },
        error: () => {
            alert("Ошибка");
        }
    })
}

function searchCargoByKeyword(){
    const keyword = $('#search').val();
    $.get("/api/v1/cargo/search/" + keyword, function (cargo){
        if(Object.keys(cargo).length === 0) {
            alert("Код не существует !!!!")
        }
        else {
            showingCargoOffCanvas(cargo)
        }
    });
}




function getCargoById(id) {
    $.get( "/api/v1/cargo/" + id, function( cargo ) {
        showingCargoOffCanvas(cargo)
    });
}


function showingCargoOffCanvas (cargo) {
    $('#inputId').val(cargo.id);
    $('#container').barcode(cargo.clientBarcode, 'code128', {barWidth: 2, barHeight: 130});
    $('#numberOfSeats').html(cargo.numberOfSeats);
    $('#weight').html(cargo.weight);
    $('#dimensions').html(cargo.dimensions);
    $('#volume').html(cargo.volume);

    if (cargo.pecCode === void 0 || cargo.pecCode === null || cargo.pecCode.trim() === '') {
        $('#pecCode').hide();
        $('#inputPecCode').show();
    } else {
        $('#inputPecCode').hide();
        $('#pecCode').show().html(cargo.pecCode);

    }
    if (cargo.processed) {
        $('#processed').hide();
        if (cargo.issuance) {
            $('#issuance').hide();

            $('#timeOfIssueAtWarehouse').show().html(cargo.timeOfIssueAtWarehouse);

            $('#cellIssuanceByUser').show();
            $('#issuanceByUser').html(cargo.issuanceByUser);
        } else {
            $('#issuance').show();
            $('#timeOfIssueAtWarehouse').hide();
            $('#cellIssuanceByUser').hide();
        }
    } else {
        $('#issuance').hide();
        $('#processed').show();
        $('#cellIssuanceByUser').hide();
    }
    if(cargo.issuanceByUser === 'null'){
        $('#cellIssuanceByUser').hide()
    }

    $('#truckName').attr("href", "/truck/" + cargo.truckId + "/cargo").html(cargo.truckName);
}