//<start id="xmla_response_class"/>
function XMLAResponse(XMLAContent) {
  this.rowHeaders = new Array();  //<co id="xmla_response_arrays"/>
  this.colHeaders = new Array();  
  this.cellData = new Array();    

  this.parseHeaders(XMLAContent);
  this.parseData(XMLAContent);
}
//<end id="xmla_response_class"/>

//<start id="xmla_response_parse_headers"/>
XMLAResponse.prototype.parseHeaders = function(XMLAContent) {
  var axisCount = 0;
  
  var xr = this; //<co id="remember_object"/>
  
  $(XMLAContent).find("Axis").each(function() {
    var lname = "";
    axisCount++;

    var levelNames = new Array();
    var headers; //<co id="xmla_response_set_header"/>
    if      (axisCount == 1) headers = xr.colHeaders;
    else if (axisCount == 2) headers = xr.rowHeaders;
    else { //<co id="xmla_response_parse_headers_only_2_axes"/>
      return;
    }
    
    var currentLevel;
    var level;
    
    $(this).find("Tuple").each(function() {
      $(this).find("Member").each(function() { //<co id="xmla_respose_parse_headers_each_member>"/>
        var caption  = $(this).find("Caption").text();
        var newlname = $(this).find("LName").text();
        if (newlname != currentLevel) {//<co id="xmla_respose_parse_headers_new_level>"/>
          level = xr.findHeaderLevel(levelNames, headers, newlname);
        }
        level.push(caption); //<co id="xmla_respose_parse_headers_add_value>"/>
      });
    });
    if (axisCount == 2) xr.rotateRowHeaders(); //<co id="xmla_respose_parse_headers_rotate_row>"/>
  });
}
//<end id="xmla_response_parse_headers"/>

//<start id="xmla_response_find_header_level"/>
XMLAResponse.prototype.findHeaderLevel = 
  function (levelNames, headers, newlname) {
  
  var level = null; //<co id="find_correct_level"/>
  for (var cnt = 0; cnt < levelNames.length; cnt++) {
    if (newlname == levelNames[cnt]) {
      level = headers[cnt];
      break;
    }
  }
  
  if (headers.length > 0) { //<co id="make_rows_equal"/>
    var max = headers[headers.length - 1].length;
    for (cnt = headers.length - 2; cnt >= 0; cnt -= 1) {
      var row = headers[cnt];
      var lval = row[row.length - 1];
      while (row.length < max) {
        row.push(lval);//<co id="populate_with_same_value"/>
      }
    }
  }
  
  if (!level) { //<co id="is_new_level"/>
    level = new Array();
    headers.push(level);
    levelNames.push(newlname);
  }

  return level;
    
}
//<end id="xmla_response_find_header_level"/>

//<start id="xmla_response_rotate_row_headers"/>
XMLAResponse.prototype.rotateRowHeaders = function () {
  var nbrOldHeaders = this.rowHeaders.length;
  if (nbrOldHeaders > 0) { //<co id="create_new_row_header_array"/>
    var newRowHeaders = new Array();
    var newLength = this.rowHeaders[0].length;
    for (var cnt = 0; cnt < newLength; cnt++) {
      newRowHeaders.push(new Array());
    }
    for (var rcnt = 0; rcnt < nbrOldHeaders; rcnt++) { //<co id="copy_data_to_new_array"/>
      for (var ccnt = 0; ccnt < newLength; ccnt++) {
        newRowHeaders[ccnt].push(this.rowHeaders[rcnt][ccnt]);
      }
    }
    this.rowHeaders = newRowHeaders; //<co id="set_new_row_headers"/>
  }
}
//<end id="xmla_response_rotate_row_headers"/>

//<start id="xmla_response_parse_data"/>
XMLAResponse.prototype.parseData = function(XMLAContent) {
  var cnt = 0;
  var rowLength = this.colHeaders[0].length;
  var data = this.cellData;

  for (var rcnt = 0; rcnt < this.rowHeaders.length; rcnt++) {
    var row = new Array(); //<co id="prepopulate_with_empty_data"/>
    data.push(row);
    for (var ccnt = 0; ccnt < rowLength; ccnt++) {
      row.push("");
    }
  }

  $(XMLAContent).find("Cell").each(function() { //co id="parse_data_cells"/>
    var value = $(this).find("FmtValue").text();
    var cellOrdinalValue = $(this).attr("CellOrdinal");
    var cloc = cellOrdinalValue % rowLength; //<co id="parse_data_column_location"/>
    var rloc = Math.floor(cellOrdinalValue / rowLength);//<co id="parse_data_row_location"/>
    data[rloc][cloc] = value;
  });
}
//<end id="xmla_response_parse_data"/>
