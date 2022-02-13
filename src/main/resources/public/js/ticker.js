function loadComments () {

    this.source = null;

    this.start = function () {

        var tickerTable = document.getElementById("tickers");

        this.source = new EventSource("/ticker/stream");

        this.source.addEventListener("message", function (event) {

            // These events are JSON, so parsing and DOM fiddling are needed
            var ticker = JSON.parse(event.data);

            var row = tickerTable.getElementsByTagName("tbody")[0].insertRow(0);
            var cell0 = row.insertCell(0);
            var cell1 = row.insertCell(1);
            var cell2 = row.insertCell(2);
            var cell3 = row.insertCell(3);
            var cell4 = row.insertCell(4);
            var cell5 = row.insertCell(5);
            var cell6 = row.insertCell(6);
            var cell7 = row.insertCell(7);
            
            cell0.className = "author-style";
            cell0.innerHTML = numberWithCommas(ticker.trade_price);
            
            cell1.className = "text";
            cell1.innerHTML = numberWithCommas(ticker.opening_price);
            
            cell2.className = "text";
            cell2.innerHTML = numberWithCommas(ticker.highest_52_week_price);
            
            cell3.className = "text";
            cell3.innerHTML = numberWithCommas(ticker.lowest_52_week_price);
               
            cell4.className = "text";
            cell4.innerHTML = ticker.change;
            
            cell5.className = "text";
            cell5.innerHTML = numberWithCommas(ticker.high_price);
            
            cell6.className = "text";
            cell6.innerHTML = numberWithCommas(ticker.low_price);
                                  
            cell7.className = "date";
            cell7.innerHTML = new Date(ticker.timestamp).toLocaleString();

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