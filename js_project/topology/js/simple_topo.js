// create an array with nodes
var operateNodeId;

function draw() {

    var nodes = new vis.DataSet();
    var edges = new vis.DataSet();
    nodes.add([{
        id: 1,
        label: 'Node 1',
        title: '<b>hi</b>',
        x: 20,
        y: 50,
        allowedToMoveX: false,
        allowedToMoveY: false
    }, {
        id: 2,
        label: 'Node 2',
        x: 80,
        y: 100,
        allowedToMoveX: false,
        allowedToMoveY: false
    }, {
        id: 3,
        label: 'Node 3',
        x: 150,
        y: 100,
        allowedToMoveX: false,
        allowedToMoveY: false,
    }, {
        id: 4,
        label: 'Node 4',
        x: 220,
        y: 70,
        allowedToMoveX: false,
        allowedToMoveY: false,
    }, {
        id: 5,
        label: 'Node 5',
        x: 340,
        y: 80,
        allowedToMoveX: false,
        allowedToMoveY: false

    }]);

    // create an array with edges
    edges.add([{
        from: 1,
        to: 2
    }, {
        from: 1,
        to: 3
    }, {
        from: 2,
        to: 4
    }, {
        from: 2,
        to: 5
    }]);

    // create a network
    var container = document.getElementById('mynetwork');
    var data = {
        nodes: nodes,
        edges: edges,
    };
    var options = {
        stabilize: true,
        width: '600px',
        height: '600px',
        edges: {
            color: 'red',
            width: 2
        },
        radius: 24
    }


    //    // provide data in the DOT language
    //    data = {
    //        dot: 'dinetwork {1 -> 1 -> 2; 2 -> 3; 2 -> 4; 2 -> 1 }'
    //    };


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