const now = moment();
let date = ''

$(document).ready(function () {
    $('#search').keydown (function(e) {
        if(e.which === 13) {
            searchCargoByKeyword($(this).val())
            $(this).val("");
        }
    });
    $('#search-button').on("click", function (){
        searchCargoByKeyword($('#search').val());
    });
});


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

function searchCargoByKeyword(keyword){
    $.get("/api/v1/cargo/search/" + keyword,
        function (cargo){
        if(Object.keys(cargo).length === 0) {
            $('#searchNotFound').show();
            $('#cargoOffCanvas').hide();

        }
        else {
            $('#cargoOffCanvas').show();
            $('#searchNotFound').hide();
            showingCargoOffCanvas(cargo);
        }
    }).fail(function (){
        $('#searchNotFound').show();
        $('#cargoOffCanvas').hide();
    });
}

function getCargoById(id) {
    $.get( "/api/v1/cargo/" + id, function( cargo ) {
        $('#cargoOffCanvas').show();
        $('#searchNotFound').hide();
        showingCargoOffCanvas(cargo);
    });
}


function showingCargoOffCanvas (cargo) {

    if(cargo.timeOfIssueAtWarehouse != null) {
        date = moment(cargo.timeOfIssueAtWarehouse).format("DD-MM-YYYY HH:mm");
        console.log(date)
    }

    $('#inputId').val(cargo.id);
    $('#container').barcode(cargo.clientBarcode, 'code128', {barWidth: 2, barHeight: 130});
    $('#numberOfSeats').html(cargo.numberOfSeats);
    $('#weight').html(cargo.weight);
    $('#dimensions').html(cargo.dimensions);
    $('#volume').html(cargo.volume);
    $('#search').val('').trigger("focus");


    if (cargo.pecCode === void 0 || cargo.pecCode === null || cargo.pecCode.trim() === '') {
        $('#pecCode').hide();
        $('#inputPecCode').show().val('');

    } else {
        $('#inputPecCode').hide().val(cargo.pecCode);
        $('#pecCode').show().html(cargo.pecCode);

    }
    if (cargo.processed) {
        $('#processed').hide();
        $("#labelProcessed").prop('checked', true)

        if (cargo.issuance) {
            $('#issuance').hide();
            $('#labelIssuance').prop('checked', true)
            $('#buttonSave').hide();

            $('#timeIssue').show();
                $('#timeOfIssueAtWarehouse').show().html(date);

            $('#cellIssuanceByUser').show();
            $('#issuanceByUser').html(cargo.issuanceByUser);
        } else {
            $('#buttonSave').show();
            $("#labelIssuance").prop('checked', false)
            $('#issuance').show();
            $('#timeIssue').hide();
            $('#timeOfIssueAtWarehouse').hide()
            $('#cellIssuanceByUser').hide();
        }
    } else {
        $('#buttonSave').show();
        $("#labelProcessed").prop('checked', false)
        $('#issuance').hide();
        $('#processed').show();
        $('#cellIssuanceByUser').hide();
        $('#timeIssue').hide();
        $('#timeOfIssueAtWarehouse').hide()
    }
    if(cargo.issuanceByUser === 'null'){
        $('#cellIssuanceByUser').hide()
    }

    $('#truckName').attr("href", "/truck/" + cargo.truckId + "/cargo").html(cargo.truckName);
}

