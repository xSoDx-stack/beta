

function searchCargoByKeyword(){
    const keyword = $('#search').val();
    $.get("/api/v1/cargo/search/" + keyword, function (data){
        if(Object.keys(data).length === 0) {
            alert("Код не существует !!!!")
        }
        else {
            showingCargoOffcanvas(data)
        }
    });
}

function getCargoById(id) {
    $.get( "/api/v1/cargo/" + id, function( data ) {
        showingCargoOffcanvas(data)
    });
}

function showingCargoOffcanvas (data) {
    $('#container').barcode(data.clientBarcode, 'code128', {barWidth: 2, barHeight: 130});
    $('#numberOfSeats').html(data.numberOfSeats);
    $('#weight').html(data.weight);
    $('#dimensions').html(data.dimensions);
    $('#volume').html(data.volume);

    if (data.pecCode === void 0 || data.pecCode === null || data.pecCode.trim() === '') {
        $('#pecCode').hide();
        $('#inputPecCode').show();
    } else {
        $('#inputPecCode').hide();
        $('#pecCode').show().html(data.pecCode);

    }
    if (data.processed) {
        $('#processed').hide();
        if (data.issuance) {
            $('#issuance').hide();

            $('#timeOfIssue').show().html(data.timeOfIssue);

            $('#cellIssuanceByUser').show();
            $('#issuanceByUser').html(data.issuanceByUser);
        } else {
            $('#issuance').show();
            $('#timeOfIssue').hide();
            $('#cellIssuanceByUser').hide();
        }
    } else {
        $('#issuance').hide();
        $('#processed').show();
    }

    $('#truckName').attr("href", "/truck/" + data.truckId + "/cargo").html(data.truckName);
}