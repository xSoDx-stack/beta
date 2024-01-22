function openOffcanvas(id) {
    $.get( "/api/v1/" + id, function( data ) {
        $('#container').barcode(data.clientBarcode, 'code128', {barWidth:2, barHeight:130});
        $('#numberOfSeats').html(data.numberOfSeats);
        $('#weight').html(data.weight);
        $('#dimensions').html(data.dimensions);
        $('#volume').html(data.volume);

        if (data.pecCode === void 0 || data.pecCode === null || data.pecCode.trim() === '') {
            $('#pecCode').hide();
            $('#inputPecCode').show();
        }
        else{
            $('#inputPecCode').hide();
            $('#pecCode').show().html(data.pecCode);

        }
        if(data.processed){
            $('#processed').hide();
            if(data.issuance){
                $('#issuance').hide();

                $('#timeOfIssue').show().html(data.timeOfIssue);

                $('#cellIssuanceByUser').show();
                $('#issuanceByUser').html(data.issuanceByUser);
            }
            else{
                $('#issuance').show();
                $('#timeOfIssue').hide();
                $('#cellIssuanceByUser').hide();
            }
        }
        else {
            $('#issuance').hide();
            $('#processed').show();
        }

        $('#truckName').attr("href", "/truck/" + data.truckId + "/cargo").html(data.truckName);
    });
    const myBackdrop = document.querySelector('#staticBackdrop');
    const bsOffcanvas = new bootstrap.Offcanvas(myBackdrop);
    bsOffcanvas.toggle();
}