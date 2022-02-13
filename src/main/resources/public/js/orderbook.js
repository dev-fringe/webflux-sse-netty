function loadComments () {

    this.source = null;

    this.start = function () {

        var orderbookTable = document.getElementById("orderbooks");

        this.source = new EventSource("/orderbook/stream");

        this.source.addEventListener("message", function (event) {

            // These events are JSON, so parsing and DOM fiddling are needed
            var orderbook = JSON.parse(event.data);

            var row = orderbookTable.getElementsByTagName("tbody")[0].insertRow(0);
            var cell0 = row.insertCell(0);
            var cell1 = row.insertCell(1);
            var cell2 = row.insertCell(2);
            
            cell0.className = "author-style";
            cell0.innerHTML = orderbook.total_ask_size;
            
            cell1.className = "text";
            cell1.innerHTML = orderbook.total_bid_size;
            
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