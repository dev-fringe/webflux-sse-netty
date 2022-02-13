function loadComments () {

    this.source = null;

    this.start = function () {

        var tradetickTable = document.getElementById("tradeticks");

        this.source = new EventSource("/candleday/stream");

        this.source.addEventListener("message", function (event) {

            // These events are JSON, so parsing and DOM fiddling are needed
            var orderbook = JSON.parse(event.data);

            var row = tradetickTable.getElementsByTagName("tbody")[0].insertRow(0);
            var cell0 = row.insertCell(0);
            var cell1 = row.insertCell(1);
            var cell2 = row.insertCell(2);
            
            cell0.className = "author-style";
            cell0.innerHTML = orderbook.ask_bid;
            
            cell1.className = "text";
            cell1.innerHTML = toPlainString(orderbook.trade_volume);
            
            cell2.className = "date";
            cell2.innerHTML = new Date(orderbook.timestamp).toLocaleString();

        });

        this.source.onerror = function () {
            this.close();
        };

    };

    this.stop = function() {
        this.source.close();
    }

}

function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function toPlainString(num) {
  return (''+ +num).replace(/(-?)(\d*)\.?(\d*)e([+-]\d+)/,
    function(a,b,c,d,e) {
      return e < 0
        ? b + '0.' + Array(1-e-c.length).join(0) + c + d
        : b + c + d + Array(e-d.length+1).join(0);
    });
}


comment = new loadComments();

/*
 * Register callbacks for starting and stopping the SSE controller.
 */
window.onload = function() {
    comment.start();
};
window.onbeforeunload = function() {
    comment.stop();
}