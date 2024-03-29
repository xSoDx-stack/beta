const now = moment();
let date = '';

$(document).ready(function () {
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    $(document).ajaxSend(function(e, xhr) {
        xhr.setRequestHeader(header, token);
    });

});


function resetData(){
    $('#personFormData')[0].reset();
}

$(document).ready(function () {
    $('#search').keydown(function (e) {
        if (e.which === 13) {
            searchCargoByKeyword($(this).val())
            $(this).val("");

        }
    });
    $('#search-button').on("click", function () {
        searchCargoByKeyword($('#search').val());

    });
});

$(document).ready(function () {
    $('#inputPecCode').keydown(function (e) {
        if (e.which === 13) {
            $('#labelProcessed').prop('checked', true)
            $('#search').focus();
            saveCargo()
        }
    });
});


function deletePecCode() {
    const id = $('#inputId').val();
    $.post("/api/v1/cargo/delete/" + id, function (cargo) {
        showingCargoOffCanvas(cargo)
    });
}

function uploadFile() {
    const data = new FormData($("#formData")[0]);
    $.ajax({
        type: "POST",
        url: "/api/v1/cargo/upload",
        cache: false,
        processData: false,
        contentType: false,
        data: data,
        dataType: "json",
        success: () => {
            $("#invalidFormatFile").hide();
            location.reload()
        },
        error: () => {
            $("#invalidFormatFile").show();
        }
    });
}


function saveCargo() {
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
            searchCargoByKeyword(cargo.clientBarcode)
        },
        error: () => {
            alert("Ошибка");
        }
    });
}

function searchCargoByKeyword(keyword) {
    if (keyword.trim() === '') {
        $('#searchNotFound').show();
        $('#cargoOffCanvas').hide();
    } else {
        $.get("/api/v1/cargo/search/" + keyword,
            function (cargo) {
                if (Object.keys(cargo).length === 0) {
                    $('#searchNotFound').show();
                    $('#cargoOffCanvas').hide();

                } else {
                    $('#cargoOffCanvas').show();
                    $('#searchNotFound').hide();
                    showingCargoOffCanvas(cargo);
                    $('#inputPecCode').focus();
                }
            }).fail(function () {
            $('#searchNotFound').show();
            $('#cargoOffCanvas').hide();
        });
    }
}

function getPersonById(id) {
    $.get("/administration/person/get/" + id, function (person) {
        $('#id').val(person.id)
        $('#username').val(person.username)
        $('#fullName').val(person.fullName)
        $('#roleId').val(person.roleId);
    });
}


function getCargoById(id) {
    $.get("/api/v1/cargo/" + id, function (cargo) {
        $('#cargoOffCanvas').show();
        $('#searchNotFound').hide();
        showingCargoOffCanvas(cargo);
    });
}


function showingCargoOffCanvas(cargo) {
    if (cargo.timeOfIssueAtWarehouse != null) {
        date = moment(cargo.timeOfIssueAtWarehouse).format("DD.MM.YYYY HH:mm");
    }

    $('#inputId').val(cargo.id);
    $('#container').barcode(cargo.clientBarcode, 'code128', {barWidth: 2, barHeight: 130});
    $('#numberOfSeats').html(cargo.numberOfSeats);
    $('#weight').html(cargo.weight);
    $('#dimensions').html(cargo.dimensions);
    $('#weightOfOnePiece').html(cargo.weightOfOnePiece);
    if (cargo.city.trim() === '') {
        $('#tableCity').hide();
    } else
        $('#tableCity').show();

    $('#city').html(cargo.city);
    $('#volume').html(cargo.volume);
    $('#search').val('').trigger("focus");


    if (cargo.pecCode === void 0 || cargo.pecCode === null || cargo.pecCode.trim() === '') {
        $('#pecCode').hide();
        $('#inputPecCode').show().val('');
        $('#trashButton').hide();
        $('#issuance').hide();

    } else {
        $('#inputPecCode').hide().val(cargo.pecCode);
        $('#pecCode').show().html(cargo.pecCode.substring(0, 12));

        if (cargo.issuance) {
            $('#trashButton').hide();
            $('#issuance').hide();
        } else {
            $('#trashButton').show();
            $('#issuance').show();
        }

    }
    if (cargo.processed) {
        $('#processed').hide();
        $("#labelProcessed").prop('checked', true)

        if (cargo.issuance) {
            $('#labelIssuance').prop('checked', true)
            $('#buttonSave').hide();

            $('#timeIssue').show();
            $('#timeOfIssueAtWarehouse').show().html(date);

            $('#cellIssuanceByUser').show();
            $('#issuanceByUser').html(cargo.issuedAtWarehouseByUser);
        } else {
            $('#buttonSave').show();
            $("#labelIssuance").prop('checked', false)

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
    if (cargo.issuanceByUser === 'null') {
        $('#cellIssuanceByUser').hide()
    }

    $('#truckName').attr("href", "/truck/" + cargo.truckId + "/cargo").html(cargo.truckName);
}

$(document).ready(function() {
    $('#personFormData').on("submit", function (event) {
        event.preventDefault();
        const formData = {
            id: $('#id').val(),
            username:$('#username').val(),
            fullName: $('#fullName').val(),
            roleId: $('#roleId').val(),
            password: $('#password').val()
        };
        $.ajax({
            url: $(this).attr("action"),
            type: $(this).attr("method"),
            data: JSON.stringify(formData),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: () => {
                location.reload();
            },
            error: function (xhr, textStatus, errorThrown) {
                $('.form-control').removeClass('is-invalid');
                $('.invalid-feedback').empty();
                handleErrors(JSON.parse(xhr.responseText));
            }
        });
    });
});

function handleErrors(errors) {
    for (let field in errors) {
        $(`[name=${field}]`).addClass('is-invalid');
        $(`[name=${field}] + .invalid-feedback`).text(errors[field]);
    }
}
