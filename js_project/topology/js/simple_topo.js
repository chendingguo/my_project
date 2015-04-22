// create an array with nodes
var operateNodeId;
var network;
var options = {};


function draw(netWorkData) {


    // create a network
    var container = document.getElementById('mynetwork');
    var data = {
        nodes: nodes,
        edges: edges,
    };
    var options = {
        stabilize: true,
        width: '600px',
        height: '400px',
        edges: {
            color: 'red',
            width: 2
        },
        radius: 24
    }



    var network = new vis.Network(container, data, options);
   
    network.on('dragStart', onDragStart);


    function onDragStart(properties) {
        operateNodeId = properties.nodeIds[0];
        console.log(operateNodeId);
        nodes.update({
            id: operateNodeId,
            allowedToMoveX: true,
            allowedToMoveY: true,

        });
    }

    network.on('select', onSelect);

    function onSelect(properties) {
        var nodeId = properties.nodes;
        console.log(nodeId);
        var positions = network.getPositions(nodeId);
        for (index in positions) {
            console.log(positions[index].x + ":" + positions[index].y);

        }
    }


    network.on('doubleClick', onDoubleClick);

    function onDoubleClick(properties) {
        network.storePositions();
        saveDatas(JSON.stringify(data));
        console.debug(nodes);
        var canvasPosition = properties.pointer.canvas;
        console.log(canvasPosition.x + " : " + canvasPosition.y);
        nodes.update({
            id: operateNodeId,
            x: canvasPosition.x,
            y: canvasPosition.y,
            allowedToMoveX: true,
            allowedToMoveY: true

        });

        nodes.update({
            id: operateNodeId,
            allowedToMoveX: false,
            allowedToMoveY: false

        });


    }


}



function saveDatas(netWorkJsonStr) {
    var url = canvas_app + "/saveNetWork";
    $.ajax({
        type: "POST",
        url: url,
        cache: false,
        data: {
            netWorkJsonStr: netWorkJsonStr
        },
        dataType: "text",
        success: function(retObj) {

            alert("success");

        },
        error: function(XMLHttpRequest) {

            alert(XMLHttpRequest);
        }

    });
}



function getNetWork(netWorkName) {
    var options = {freezeForStabilization:true};
    var nodes = new vis.DataSet(options);
    var edges = new vis.DataSet(options);
    var url = canvas_app + "/getNetWork";
    $.ajax({
        type: "POST",
        url: url,
        cache: false,
        data: {
            netWorkName: netWorkName
        },
        dataType: "text",
        success: function(returnObj) {
            var container = document.getElementById('mynetwork');
            var data = $.parseJSON(returnObj);
            nodes.add(data.nodes);
            edges.add(data.edges);
            var netData = {
                nodes: nodes,
                edges: edges
            };

            network = new vis.Network(container, netData, options);
            network.freezeSimulation(true);
            network.on('dragStart', onDragStart);


            function onDragStart(properties) {
				
                operateNodeId = properties.nodeIds[0];
                console.log(operateNodeId);
                nodes.update({
                    id: operateNodeId,
                    allowedToMoveX: true,
                    allowedToMoveY: true,

                });
            }
			
			
			 network.on('dragEnd', onDragEnd);


            function onDragEnd(properties) {
				
              
            }

            network.on('select', onSelect);

            function onSelect(properties) {
                var nodeId = properties.nodes;
                console.log(nodeId);
                var positions = network.getPositions(nodeId);
                for (index in positions) {
                    console.log(positions[index].x + ":" + positions[index].y);

                }
            }


            network.on('doubleClick', onDoubleClick);

            function onDoubleClick(properties) {
                network.storePositions();
                saveDatas(JSON.stringify(netData));
                console.debug(nodes);
                var canvasPosition = properties.pointer.canvas;
                console.log(canvasPosition.x + " : " + canvasPosition.y);
               
              


            }
        },
        error: function(XMLHttpRequest) {

            alert("failed");
        }

    });
}