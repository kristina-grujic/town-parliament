<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns="http://www.parlament.gov.rs/amandmani"
	xmlns:ns2="http://www.parlament.gov.rs/amandmani"
	ns2:id="1"
	ns2:odobreno="false">
    <xsl:template match="/">
        <html>

        	<head>
        		<title><xsl:value-of select="ns2:dokument/ns2:naslov"/></title>

        	</head>
            <body>
            	<div >
            		<div >
	                	<h2>Predlog</h2>
	                </div>

	                <div >
	                	<xsl:value-of select="ns2:dokument/ns2:naslov"/>
	                </div>

	                <div >
		                <label>Sluzbeni list</label>
		                <div >

		                	<div >
		                		Broj lista:
		                		<div >
		                			<xsl:value-of select="ns2:dokument/ns2:sluzbeniList/ns2:broj_lista"/>
		                		</div>
		                	</div>

		                	<div >
		                		Cena:
		                		<div >
		                			<xsl:value-of select="ns2:dokument/ns2:sluzbeniList/ns2:cena"/>
		                		</div>
		                	</div>

		                	<div >
		                		Mesto:
		                		<div >
		                			<xsl:value-of select="ns2:dokument/ns2:sluzbeniList/ns2:mestoDatum/ns2:mesto"/>
		                		</div>
		                	</div>

		                	<div >
		                		Datum:
		                		<div >
		                			<xsl:value-of select="ns2:dokument/ns2:sluzbeniList/ns2:mestoDatum/ns2:datum"/>
		                		</div>
		                	</div>

            			</div>
            		</div>


            		<div >
            			<xsl:for-each select="ns2:dokument/ns2:propisi">
            				<div >
            					<label>Propis: </label>
            					<div >

            						<div >
            							Naziv propisa:
            							<div >
											<p><xsl:value-of select="ns2:naziv_propisa"/></p>
										</div>
            						</div>

									<div class="name-propis">
            							Preambula:
            							<div >
											<p ><xsl:value-of select="ns2:preambula"/></p>
										</div>
            						</div>

            						<div >
            							<xsl:for-each select="ns2:uvodniDeo">

            								<label >Uvodni deo: </label>

            								<div >

            									<xsl:for-each select="ns2:glava">
	            									<div >
	            										Glava
	            										<div >
	            											Naslov glave:
	            											<div >
	            												<xsl:value-of select="ns2:podaciGlave/ns2:naslovGlave"/>
	            											</div>
	            											Podnaslov glave:
	            											<div >
	            												<xsl:value-of select="ns2:podnaslovGlave"/>
	            											</div>

	            											<div >

	            												<xsl:for-each select="ns2:clan">

	            													<div style="margin: 1% 0 1% 5%">
		            													CLAN:
		            													<div >
		            														Naslov clana:
			            													<div >
																				<xsl:value-of select="ns2:podaciClana/ns2:naslovClana"/>
																			</div>
																			Opis:
			            													<div >
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
            							</xsl:for-each>
            						</div>

            						<xsl:if test="ns2:glavniDeo/ns2:glava/ns2:podaciGlave/ns2:naslovGlave!=''">
            						<div >
            							<xsl:for-each select="ns2:glavniDeo">

            								<label style="text-align: center;">Glavni deo: </label>

            								<div >

            									<xsl:for-each select="ns2:glava">
	            									<div >
	            										Glava
	            										<div >
	            											Naslov glave:
	            											<div >
	            												<xsl:value-of select="ns2:podaciGlave/ns2:naslovGlave"/>
	            											</div>
	            											Podnaslov glave:
	            											<div >
	            												<xsl:value-of select="ns2:podnaslovGlave"/>
	            											</div>

	            											<div >

	            												<xsl:for-each select="ns2:clan">

	            													<div style="margin: 1% 0 1% 5%">
		            													CLAN:
		            													<div >
		            														Naslov clana:
			            													<div >
																				<xsl:value-of select="ns2:podaciClana/ns2:naslovClana"/>
																			</div>
																			Opis:
			            													<div >
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

            							</xsl:for-each>
            						</div>
            						</xsl:if>

            						<xsl:if test="ns2:zavrsniDeo/ns2:glava/ns2:podaciGlave/ns2:naslovGlave!=''">
            						<div >
            							<xsl:for-each select="ns2:zavrsniDeo">
            								<label >Zavrsni deo: </label>

            								<div >

            									<xsl:for-each select="ns2:glava">
	            									<div >
	            										Glava
	            										<div >
	            											Naslov glave:
	            											<div>
	            												<xsl:value-of select="ns2:podaciGlave/ns2:naslovGlave"/>
	            											</div>
	            											Podnaslov glave:
	            											<div>
	            												<xsl:value-of select="ns2:podnaslovGlave"/>
	            											</div>

	            											<div >

	            												<xsl:for-each select="ns2:clan">

	            													<div >
		            													CLAN:
		            													<div >
		            														Naslov clana:
			            													<div>
																				<xsl:value-of select="ns2:podaciClana/ns2:naslovClana"/>
																			</div>
																			Opis:
			            													<div >
																				<xsl:value-of select="ns2:opis"/>
																			</div>
																		</div>
																	</div>

	            												</xsl:for-each>

	            											</div>
	            										</div>
	            									</div>
            									</xsl:for-each>

            									<div >
            										<xsl:value-of select="ns2:potpis_presednika"/>
            									</div>
            								</div>
            							</xsl:for-each>
            						</div>
									</xsl:if>

									<xsl:if test="ns2:dopunaZakona/ns2:glava/ns2:podaciGlave/ns2:naslovGlave!=''">
            						<div >
            							<xsl:for-each select="ns2:zavrsniDeo">
            								<label >Amandman: </label>

            								<div >

            									<xsl:for-each select="ns2:glava">
	            									<div >
	            										Glava
	            										<div >
	            											Naslov glave:
	            											<div >
	            												<xsl:value-of select="ns2:podaciGlave/ns2:naslovGlave"/>
	            											</div>
	            											Podnaslov glave:
	            											<div >
	            												<xsl:value-of select="ns2:podnaslovGlave"/>
	            											</div>

	            											<div >

	            												<xsl:for-each select="ns2:clan">

	            													<div >
		            													CLAN:
		            													<div >
		            														Naslov clana:
			            													<div >
																				<xsl:value-of select="ns2:podaciClana/ns2:naslovClana"/>
																			</div>
																			Opis:
			            													<div  >
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
            							</xsl:for-each>
            						</div>
									</xsl:if>


            					</div>
            				</div>
            			</xsl:for-each>
            		</div>
            	</div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
