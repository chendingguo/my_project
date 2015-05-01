// Checks to see if there was an error.  If so, show an alert and return true.
// -- response - The response from the XMLA SOAP call.
// <start id="check_for_xmla_error"/>
function checkForXMLAError(response) {
		var hasError = false;
		$(response).find('error').each(function () { // <co
			// id="has_xmla_error"/>
			hasError = true;
			var code = $(this).find('code').text(); // <co
			// id="has_xmla_error_code"/>
			var desc = $(this).find('desc').text(); // <co
			// id="has_xmla_error_description"/>
			alert('found an error (' + code + '): ' + desc);
		});
		return hasError;
	}
	// <end id="check_for_xmla_error"/>

// SOAP header and footer to be added to all messages. Replace xxxMESSAGExxx
// with the actual message.
// <start id="soap_message_template"/>
var SOAPMessage = '<SOAP-ENV:Envelope \
    xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" \
    SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"> \
  <SOAP-ENV:Header />\
  <SOAP-ENV:Body> \
    xxxMESSAGExxx\
  </SOAP-ENV:Body> \
</SOAP-ENV:Envelope>';
// <end id="soap_message_template"/>

// Returns the complete SOAP message with the content embedded.
// <start id="get_soap_message"/>
function getSOAPMessage(content) {
		return SOAPMessage.replace("xxxMESSAGExxx", content);
	}
	// <end id="get_soap_message"/>

// Message to discover the available catalogs and cubes.
// <start id="discover_datasources_query"/>
var discoverDatasourcesQuery = '<Discover xmlns="urn:schemas-microsoft-com:xml-analysis">\
  <RequestType>DISCOVER_DATASOURCES</RequestType>\
  <Restrictions>\
    <RestrictionList/>\
  </Restrictions>\
  <Properties>\
    <PropertyList>\
      <Format>Tabular</Format>\
    </PropertyList>\
  </Properties>\
</Discover>';

function getDiscoverMessage() {
		return getSOAPMessage(discoverDatasourcesQuery);
	}
	// <end id="discover_datasources_query"/>

// <start id="datasource_variable"/>
var datasource;
// <end id="datasource_variable"/>
// <start id="catalogs_variable"/>
var catalogs;
// <end id="catalogs_variable"/>

// Called when the discovery message is responded to.
// -- data - data received from the server
// -- textStatus - status of the results
// -- jqXHR - todo - figure out what this is
// <start id="handle_discover_callback"/>
var html = "";

function handleDiscoverCallback(data, textStatus, jqXHR) {
		if (checkForXMLAError(data) == true) {
			return;
		} // <co id="handle_discover_callback_error_return"/>
		html = "";
		$("#datasourcesSelect").html("");
		datasource = {};
		catalogs = {};

		$(data)
			.find('row').each(
				function () { // <co
					// id="handle_discover_callback_each_row"/>

					datasource.dataSourceName = // <co
						// id="handle_discover_callback_properties"/>
						$(this).find('DataSourceName').text();
					datasource.dataSourceDescription = $(this).find(
						'DataSourceDescription').text();
					datasource.url = $(this).find('URL').text();
					datasource.dataSourceInfo = $(this).find(
						'DataSourceInfo').text();
					datasource.providerName = $(this).find('ProviderName')
						.text();
					datasource.providerType = $(this).find('ProviderType')
						.text();
					datasource.authenticationMode = $(this).find(
						'AuthenticationMode').text();
					var datasourceSelOption = "<option value='" + datasource.dataSourceName + "'>" + datasource.dataSourceName + "</option>";
					$("#datasourcesSelect").append(datasourceSelOption);
					

						postMessage(
							getDiscoverCatalogsMessage(),
							'xml', // <co
							// id="handle_discover_callback_get_catalogs"/>
							function (catalogData, catalogTextStatus, jqXHR) {
								$(catalogData)
									.find('row').each(
										function () { // <co
											// id="handle_discover_callback_get_catalogs_callback"/>
											var catalogName = $(this).find('CATALOG_NAME').text();

											postMessage(
												// <co
												// id="handle_discover_callback_get_catalog_cubes"/>
												getDiscoverCubesMessage(datasource.dataSourceName, catalogName), 'xml', handleDiscoverCube);

										});
							});
					
					sleep(1000);

				});
	}
	// <end id="handle_discover_callback"/>

// <start id="discover_catalogs_query"/>
var discoverCatalogsQuery = '<Discover xmlns="urn:schemas-microsoft-com:xml-analysis">\
   <RequestType>DBSCHEMA_CATALOGS</RequestType>\
   <Restrictions />\
   <Properties />\
</Discover>';

function getDiscoverCatalogsMessage() {
		return getSOAPMessage(discoverCatalogsQuery);
	}
	// <end id="discover_catalogs_query"/>

/**
 * Sets the catalog select. Clearing out each time since entries can change.
 */
// <start id="set_catalog_select"/>
function setCatalogSelect() {

		for (var catalogName in catalogs) {
			var catalog = catalogs[catalogName];
			var cubes = catalog.cubes;
			for (var i = 0; i < cubes.length; i++) {
				html += "<option value='" + catalogName + "'>" + catalogName + " - " + cubes[i] + "</option>";
			}
		}
		$("#catalogSelect").append(html);
	}
	// <end id="set_catalog_select"/>

// <start id="handle_discover_cube"/>
function handleDiscoverCube(cubeData, textStatus, jqXHR) {

		if (checkForXMLAError(cubeData) == true) {
			return;
		} // <co id="handle_discover_cube_error_return"/>

		$(cubeData).find('row').each(function () { // <co
			// id="handle_discover_cube_each_row"/>
			var catalogName = $(this).find('CATALOG_NAME').text(); // <co
			// id="handle_discover_cube_properties"/>
			var cubeName = $(this).find('CUBE_NAME').text();
			var description = $(this).find('DESCRIPTION').text();

			var catalog = catalogs[catalogName];
			if (catalog == null) {
				catalog = new Object(); // <co
				// id="handle_discover_cube_new_catalog"/>
				catalog.catalogName = catalogName;
				catalog.cubes = new Array();
				catalogs[catalogName] = catalog;

			}
			catalog.cubes.push(cubeName);
			// <co id="handle_discover_cube_add_cube_to_catalog"/>

		});
		setCatalogSelect(); // <co id="handle_discover_cube_update_select"/>
	}
	// <end id="handle_discover_cube"/>

// <start id="discover_cubes_query"/>
var discoverCubesQuery = '<Discover xmlns="urn:schemas-microsoft-com:xml-analysis">\
   <RequestType>MDSCHEMA_MEASURES</RequestType>\
   <Restrictions>\
      <RestrictionList>\
         <CATALOG_NAME>xxxCATALOGxxx</CATALOG_NAME>\
      </RestrictionList>\
   </Restrictions>\
   <Properties>\
      <PropertyList>\
         <DataSourceInfo>xxxDATA_SOURCE_INFOxxx</DataSourceInfo>\
         <Catalog>xxxCATALOGxxx</Catalog>\
         <Format>Tabular</Format>\
      </PropertyList>\
   </Properties>\
</Discover>';

function getDiscoverCubesMessage(dataSourceInfo, catalog) {
		return getSOAPMessage(discoverCubesQuery.replace(/xxxDATA_SOURCE_INFOxxx/g,
			dataSourceInfo).replace(/xxxCATALOGxxx/g, catalog));
	}
	// <end id="discover_cubes_query"/>

// Basic string for execution message.
// TODO replace the datasource, catalog, and format.
// <start id="execute_mdx_query"/>
var executeQuery = '<Execute xmlns="urn:schemas-microsoft-com:xml-analysis"> \
   <Command> \
      <Statement>xxxMDX_STATEMENTxxx</Statement> \
   </Command> \
   <Properties> \
      <PropertyList> \
        <DataSourceInfo>xxxDATASOURCE_INFOxxx</DataSourceInfo> \
        <Catalog>xxxCATALOGxxx</Catalog> \
        <Format>Multidimensional</Format> \
        <AxisFormat>TupleFormat</AxisFormat> \
      </PropertyList> \
   </Properties> \
</Execute>';

function getQueryMessage(mdxQuery, dataSourceInfo, catalog) {
		return getSOAPMessage(executeQuery
			.replace(/xxxMDX_STATEMENTxxx/g, mdxQuery).replace(
				/xxxDATASOURCE_INFOxxx/g, dataSourceInfo).replace(
				/xxxCATALOGxxx/g, catalog));
	}
	// <end id="execute_mdx_query"/>

// Called when the query message is responded to.
// -- data - data received from the server
// -- textStatus - status of the results
// -- jqXHR - http header request
// <start id="handle_query_callback"/>
function handleQueryCallback(data, textStatus, jqXHR) {
		if (checkForXMLAError(data) == true) {
			return; // <co id="handle_data_mdx_error"/>
		}

		var response = new XMLAResponse(data);

		$("#results tr").remove();
		for (var ccnt = 0; ccnt < response.colHeaders.length; ccnt++) {
			var row = "<tr>"; // <co
			// id="handle_query_callback_each_column_header"/>
			var ch = response.colHeaders[ccnt];
			for (var rcnt = 0; rcnt < response.rowHeaders[0].length; rcnt++) {
				row += "<th></th>"; // <co
				// id="handle_query_callback_add_blank_row_header"/>
			}
			for (var ccnt2 = 0; ccnt2 < ch.length; ccnt2++) { // <co
				// id="handle_query_callback_add_col_header"/>
				row += "<th>" + ch[ccnt2] + "</th>";
			}
			row += "</tr>";
			$("#results > tbody:last").append(row);
		}

		for (var rcnt = 0; rcnt < response.rowHeaders.length; rcnt++) {
			var rh = response.rowHeaders[rcnt];
			var data = response.cellData[rcnt];
			row = "<tr>"; // <co id="handle_query_callback_each_row_header"/>

			for (rcnt2 = 0; rcnt2 < rh.length; rcnt2++) { // <co
				// id="handle_query_callback_add_row_header"/>
				row += "<th>" + rh[rcnt2] + "</th>";
			}
			for (dcnt = 0; dcnt < data.length; dcnt++) { // <co
				// id="handle_query_callback_add_data"/>
				row += "<td>" + data[dcnt] + "</td>";
			}
			row += "</tr>";
			$("#results > tbody:last").append(row);
		}

	}
	// <end id="handle_query_callback"/>

// <start id="find_query_form_element"/>
function queryFormElement(elementName) {
		var el = $("#queryForm").find('input[name="' + elementName + '"]');
		return el;
	}
	// <end id="find_query_form_element"/>

// Posts a message to Mondrian and calls the successCallback
// -- message - The message to post. This must be a valid SOAP message.
// -- successCallback - The fuction to call when a response is received.
// -- returnDataType - The type of data to return: xml or text
// <start id="post_message"/>
function postMessage(message, returnDataType, successCallback) {
		var baseURL = queryFormElement("serverURL").val(); // <co
		// id="post_message_get_parameters>"/>
		var userid = queryFormElement("userId").val();
		var password = queryFormElement("password").val();

		if (baseURL == '') { // <co id="post_message_check_URL"/>
			alert('Error: you must set the server URL prior to any calls');
			return;
		}

		var url = baseURL + "?"; // <co id="post_message_check_user"/>
		if (userid != '') {
			url += "userid=" + userid + "&";
		}
		if (password != '') {
			url += "password=" + password;
		}

		$.ajax({ // <co id="post_message_using_ajax"/>
			type: 'POST',
			url: url,
			contentType: "text/xml",
			data: message,
			success: successCallback,
			dataType: returnDataType
		});
	}
	// <end id="post_message"/>

/* display any ajax errors. */
// <start id="ajax_errors"/>
$("#errors").ajaxError(function (event, request, settings) {
	$(this).append("Error requesting page " + settings.url);
});
// <end id="ajax_errors"/>

// <start id="document_ready"/>
$(document).ready(
	function () {

		// <start id="discover_button_onclick"/>
		queryFormElement("discoverButton").click(
			function () {
				postMessage(getDiscoverMessage(), 'xml',
					handleDiscoverCallback);
			});
		// <end id="discover_button_onclick"/>

		// <start id="query_button_onclick"/>
		queryFormElement("queryButton").click(
			function () {
				var mdxQuery = $("#queryForm").find(
					'textarea[name=mdxQuery]').val();
				var dataSourceInfo = $("#datasourcesSelect").val();
				var catalog =catalogs[$("#catalogSelect").val()].catalogName;
				//var catalog = "SteelWheels";
               alert(catalog);
				if (mdxQuery == "") { 
					alert("Error: No MDX query specified.");
					return;
				}

				postMessage(getQueryMessage(mdxQuery, dataSourceInfo,
					catalog), 'xml', handleQueryCallback);
			});
		// <end id="query_button_onclick"/>

	});

function sleep(numberMillis) {
	var now = new Date();
	var exitTime = now.getTime() + numberMillis;
	while (true) {
		now = new Date();
		if (now.getTime() > exitTime)
			return;
	}
}



// <end id="document_ready"/>