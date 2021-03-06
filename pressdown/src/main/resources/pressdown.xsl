<?xml version="1.0"?>

<xsl:stylesheet version="2.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

   <xsl:param name="css">
     <!-- <xsl:value-of select="unparsed-text('file:style.css')"/> -->
   </xsl:param>
   
  <xsl:template match="*">
    <xsl:copy>
      <xsl:copy-of select="@*"/>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

  <xsl:template match="html">
    <xsl:copy>
      <xsl:copy-of select="@*"/>
      <head>
        <!--link href="style.css" rel="stylesheet"/-->
        <style>
         <xsl:value-of select="$css"/>
        </style>
      </head>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

  <xsl:template match="body">
  
    <xsl:copy>
    
      <xsl:copy-of select="@*"/>
      
      <div class="no-support-message">
        Your browser doesn't support impress.js. Try Chrome or Safari.
      </div>
      
      <div id="impress">

        <xsl:for-each-group select="*" group-starting-with="h1|h2|p">
          <div class="step slide" data-x="{position() * 1100}" data-rotate="{position() * 30}">
            <xsl:copy-of select="current-group()"/>
          </div>
        </xsl:for-each-group>

	      <div class="step" data-x="2200" data-scale="3"> </div>

      </div>
      
      <div id="madewith">Made with <a href="https://github.com/custommonkey/pressdown">Pressdown</a></div>
      <!--script type="text/javascript" src="file:///Users/jmartin/Dropbox/impress.js/js/impress.js"></script-->

      <script type="text/javascript" src="https://raw.github.com/bartaz/impress.js/0.5.3/js/impress.js"></script>
      <script>impress().init();</script>
      
    </xsl:copy>
    
  </xsl:template>

</xsl:stylesheet>
