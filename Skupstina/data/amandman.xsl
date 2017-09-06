<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns="http://www.parlament.gov.rs/amandmani"
	xmlns:ns2="http://www.parlament.gov.rs/amandmani"
	ns2:idAkta="1"
	ns2:linkAkta="11609307831103418758"
	ns2:odobreno="false"
	ns2:dopunaIzmena="false">
	<xsl:template match="/">

		<html>

			<head>
				<title><xsl:value-of select="ns2:amandman/ns2:naslovAkta"/></title>

				        		<style type="text/css">
        			.container {
        				float: left;
        				font-family: verdan;
        				margin: 0 11% 0 11%;
        				color: #565656;
        			}
        			.header {
        				width: 1000px;
        				height: 100px;
        				color: #FFF;
        				background-color: #333;
        				text-align: center;
        				padding: 4px 0 0 0;
        				font-size: 30px
        			}
        			.naslov {
        				background-color: #f0f0f0;
        				width: 1000px;
        				height: 50px;
        				margin: 3% 0 0 0;
        				text-align: center;
        				padding: 10px 0 0 0;
        				font-size: 30px
        			}
        			.sluzbeni {
        				float: left;
        				border-top: 1px solid #555;
        				width: 950px;
        				height: 75px;
        				margin: 0 0 1% 0;
        				padding: 0 0 0 3.9%;
        			}
        			.sluzbeniList {
        				float: left;
        				width: 200px;
        				height: 65px;
        				margin: 1% 0 0 0;
        				padding: 0 0 0 2%;


        			}
        			.element {
        				float: left;
        				text-align: center;
        				font-size: 16px;
        				color: #565656;
        				background-color: #f0f0f0;
        				width: 198px;
        				height: 43px;

        			}
        			.propis {
        				float: left;
        				width: 1000px;
        				margin: 1% 0 0 0;
        			}
        			.propis-element {
        				float: left;
        				border-top: 1px solid #555;
        				border-bottom: 1px solid #555;
        				width: 930px;
        				padding: 1% 0 0 5%;
        			}
        			.name-propis {
        				float: left;
        				width: 810px;
        				height: 67px;
        				margin: 0 1% 0 0;
        				padding: 1%;
        			}
        		</style>
			</head>

			<body>
				<div class="container">
					<div class="header">
	                	<h2>Amandman</h2>
	                </div>

	                <div class="naslov">
	                	<xsl:value-of select="ns2:amandman/ns2:naslovAkta"/>
	                </div>

	                <div style="margin: 2% 0 2% 0">
	                	<label>Sluzbeni list</label>
	                	<div class="sluzbeni">

	                		<div class="sluzbeniList">
		                		Broj sluzbenog lista:
		                		<div class="element">
		                			<xsl:value-of select="ns2:amandman/ns2:sluzbeniListAmandmana/ns2:brojListaAkta"/>
		                		</div>
		                	</div>

		                	<div class="sluzbeniList">
		                		Id akta:
		                		<div class="element">
									<!-- Ovde ide nesto -->
		                			<xsl:value-of select="ns2:amandman/ns2:sluzbeniListAmandmana/ns2:idAkta"/>
		                		</div>
		                	</div>

		                	<div class="sluzbeniList">
		                		Mesto:
		                		<div class="element">

		                			<xsl:value-of select="ns2:amandman/ns2:sluzbeniListAmandmana/ns2:mestoDatum/ns2:mesto"/>
		                		</div>
		                	</div>

		                	<div class="sluzbeniList">
		                		Datum:
		                		<div class="element">
		                			<xsl:value-of select="ns2:amandman/ns2:sluzbeniListAmandmana/ns2:mestoDatum/ns2:datum"/>
		                		</div>
		                	</div>
	                	</div>

	                	<div class="propis">
	                		<div style="float:left; margin: 2% 0 2% 0">
	                			<div class="propis-element">
	                				<div style="float:left; margin: 3% 0 0 1; text-align: center; width: 850px; ">
	                					<label style="text-align: center;">Amandman: </label>

	                					<div>
	                						<xsl:for-each select="ns2:amandman/ns2:dopunaZakonaAmandmana/ns2:glava">

	                							<div style="text-align: center;">
	                								Glava
	                								<div style="float: left; border-top: 1px solid #555; border-bottom: 1px solid #555; margin: 0 0 1% 3%; width: 790px; text-align: left;">

	                									Naslov glave:
	                									<div style="float: left;text-align: center; font-size: 16px; color: #565656; background-color: #f0f0f0; width: 790px; height: 43px;  padding: 1% 0 0 0">
            												23
            												<xsl:value-of select="ns2:podaciGlave/ns2:naslovGlave"/>
            											</div>
            											<br/>
            											Podnaslov glave:
            											<div style="float: left;text-align: center; font-size: 16px; color: #565656; background-color: #f0f0f0; width: 790px; height: 43px;  padding: 1% 0 0 0;">
            												23
            												<xsl:value-of select="ns2:podnaslovGlave"/>
            											</div>

            											<div style="width: 720px border-top: 1px solid #555; margin: 2% 0 0 0; text-size: 16px">

            												<xsl:for-each select="ns2:clan">

            													<div style="margin: 1% 0 1% 5%">
	            													CLAN:
	            													<div style="border-top: 1px solid #555; width: 705px;">
	            														Naslov clana:
		            													<div style="text-align: center; font-size: 16px; color: #565656; background-color: #f0f0f0; width: 705px; height: 43px;  padding: 1% 0 0 0">
																			<xsl:value-of select="ns2:podaciClana/ns2:naslovClana"/>
																		</div>
																		Opis:
		            													<div style="text-align: left; font-size: 16px; color: #565656; background-color: #f0f0f0; width: 705px; min-height: 43px; height: auto;  padding: 1% 0 0 0">
																			<xsl:value-of select="ns2:opis"/>
																		</div>
																	</div>
																</div>

            												</xsl:for-each>

            											</div>

	                								</div>
	                							</div>

	                						</xsl:for-each>

	                					</div>

	                				</div>
	                			</div>
	                		</div>
	                	</div>

	                </div>
				</div>
			</body>

		</html>


	</xsl:template>
</xsl:stylesheet>
