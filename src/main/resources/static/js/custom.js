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
            saveCargoWarehouse()
        }
    });
});

$(document).ready(function () {
    $('#inputRelabeling').keydown(function (e) {
        if (e.which === 13) {
            const cargo ={
                id: $('#inputId').val(),
                pecCode: $('#inputRelabeling').val()
            };

            $.ajax({
                url: '/api/v1/cargo/update/relabeling',
                type: 'POST',
                data: JSON.stringify(cargo),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: (cargo) => {
                    getCargoById(cargo.id)
                },
                error: () => {
                    alert("Поле не может быть пустым");
                }
            });
        }
    });
});



function deletePecCode() {
    const id = $('#inputId').val();
    $.post("/api/v1/cargo/delete/" + id, function (cargo) {
        showingCargoOffCanvas(cargo)
    });
}

function relabelingPecCode(){
    const id = $('#inputId').val();
    $.post("/api/v1/cargo/relabeling/" + id, function (cargo) {
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

function saveCargoWarehouse() {
    let offCanvas = $('#staticBackdrop');
    let cargo = {
        id: offCanvas.find("#inputId").val(),
        pecCode: offCanvas.find('#inputPecCode').val(),
        processed: $("#labelProcessed").prop('checked'),
        issuance: $("#labelIssuance").prop('checked')
    };
    ajaxSave(cargo);
}

function ajaxSave(cargo){
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

function cargoUpdateClient() {
    const currentSrc = $('#imgSave').attr('src');
    let cargo = {
        id: $('#inputId').val(),
        note: $('#inputEditNote').val(),
    }
    if (currentSrc === '/images/pencil.svg') {
        $('#imgSave').attr('src', '/images/floppy.svg');
        $('#inputEditNote').prop('readonly', false);

    } else {
        $('#imgSave').attr('src', '/images/pencil.svg');
    }
    $.ajax({
        url: '/api/v1/cargo/update',
        type: 'POST',
        data: JSON.stringify(cargo),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: (cargo) => {
            getCargoById(cargo.id)
        },
        error: () => {
            alert("Нельзя изменять выданный груз");
            getCargoById($('#inputId').val())

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

function getCargoById(id) {
    $.get("/api/v1/cargo/" + id, function (cargo) {
        $('#cargoOffCanvas').show();
        $('#searchNotFound').hide();
        return showingCargoOffCanvas(cargo);
    });
}

function getNextCargo(){
    $.get("/api/v1/cargo/next")
        .done(function (cargo) {
            showingCargoOffCanvas(cargo);
        })
        .fail(function (){
            alert("Больше нет обработанного груза, нужно подождать пока склад обработает груз");
        });
}

function showingCargoOffCanvas(cargo) {
    let date = '';
    if (cargo.timeOfIssueAtWarehouse != null) {
        date = moment(cargo.timeOfIssueAtWarehouse).format("DD.MM.YYYY HH:mm");
    }

    $('#inputId').val(cargo.id);
    $('#container').barcode(cargo.clientBarcode, 'code128', {barWidth: 2, barHeight: 130});
    $('#numberOfSeats').text(cargo.numberOfSeats);
    $('#weight').text(cargo.weight);
    $('#dimensions').text(cargo.dimensions);
    $('#weightOfOnePiece').text(cargo.weightOfOnePiece ? cargo.weightOfOnePiece : '---');
    toggleElement('#tableCity', cargo.city.trim() !== '');
    $('#city').text(cargo.city ? cargo.city : '---');
    $('#volume').text(cargo.volume);
    $('#search').val('').trigger("focus");
    $('#clientCode').text(cargo.clientBarcode);
    $('#recipient').text(cargo.recipient ? cargo.recipient : '---');
    $('#localOrTransshipment').text(cargo.localOrTransshipment ? cargo.localOrTransshipment : '---');
    $('#toTheDoor').text(cargo.toTheDoor ? cargo.toTheDoor : '---');
    $('#payer').text(cargo.payer ? cargo.payer : '---' );
    $('#phoneNumber').text(cargo.phoneNumber ? cargo.phoneNumber : '---');
    $('#issuedToClientByUser').text(cargo.issuedToClientByUser ? cargo.issuedToClientByUser : '---')
    $('#timeOfIssueToClient').text(cargo.timeOfIssueToClient ? moment(cargo.timeOfIssueToClient).format("DD.MM.YYYY HH:mm")  : '---')
    $('#shippingToRegions').prop('checked', cargo.shippingToRegions);

    if(!!cargo.note && cargo.note.trim()){
        $('#inputEditNote').text(cargo.note).prop('readonly', 'readonly');
        $('#imgSave').attr('src', '/images/pencil.svg' )

    }else{
        $('#imgSave').attr('src', '/images/floppy.svg' )
        $('#inputEditNote').text(cargo.note).prop('readonly', false);
    }


    if (!cargo.pecCode) {
        $('#pecCode').hide();
        $('#inputPecCode').show().val('');
        $('#inputRelabeling').show().val('');
        $('#trashButton').hide();
        $('#relabeling').hide();
        $('#issuance').hide();
    } else {
        if(cargo.shippingToRegions){
            $('#oldPecCode').text(cargo.oldPecCode.substring(0, 12)).css('text-decoration', 'line-through').show();
            $('#colorPecCell').addClass("yellow")
        }
        else{
            $('#oldPecCode').hide();
            $('#colorPecCell').removeClass("yellow")
        }
        $('#inputPecCode').hide().val(cargo.pecCode);
        $('#inputRelabeling').hide().val(cargo.pecCode)
        $('#pecCode').show().html(cargo.pecCode.substring(0, 12));
        toggleElement('#trashButton', !cargo.issuance);
        toggleElement('#relabeling', !cargo.issuance);
        toggleElement('#issuance', !cargo.issuance);

    }

    if (cargo.processed) {
        $('#processed').hide();
        $("#labelProcessed").prop('checked', true);
        if(cargo.issuance){
            $('#labelIssuance').prop('checked', true);
            $('#buttonSave').hide();
            toggleElement('#timeIssue', true);
            $('#timeOfIssueAtWarehouse').show().text(date);
            $('#issuanceByUser').text(cargo.issuedAtWarehouseByUser ? cargo.issuedAtWarehouseByUser : '---');
            $('#clientIssue').hide()
        }else {
            if(cargo.clientIssue){
                $("#labelIssuance").prop('checked', false);
                $('#clientIssue').hide()
                $('#buttonSave').show();
            }else {
                $('#issuance').hide()
                $('#clientIssue').show()
                $('#buttonSave').hide();
            }

            toggleElement('#timeIssue', false);
            $('#timeOfIssueAtWarehouse').hide();


        }
    }else {
        $('#buttonSave').show();
        $("#labelProcessed").prop('checked', false);
        $('#issuance').hide();
        $('#processed').show();
        $('#timeOfIssueAtWarehouse').hide();
        toggleElement('#timeIssue', false);
        $('#clientIssue').hide()
    }

    toggleElement('#cellIssuanceByUser', cargo.issuanceByUser !== 'null');
    $('#truckName').attr("href", "/truck/" + cargo.truckId + "/cargo").html(cargo.truckName);
}

function toggleElement(element, condition) {
    $(element).toggle(condition);
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
