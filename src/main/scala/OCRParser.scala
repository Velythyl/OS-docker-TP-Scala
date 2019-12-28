import java.io.{File, FileInputStream, InputStream}

import javax.imageio.ImageIO
import javax.imageio.spi.IIORegistry
import net.sourceforge.tess4j.Tesseract
import org.apache.pdfbox.jbig2.JBIG2ImageReaderSpi
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.{ImageType, PDFRenderer}

class OCRParser(languages: String = "eng") {
  CLibrary.INSTANCE.setlocale(CLibrary.LC_ALL, "C")
  CLibrary.INSTANCE.setlocale(CLibrary.LC_NUMERIC, "C")
  CLibrary.INSTANCE.setlocale(CLibrary.LC_CTYPE, "C")

  def parsePDF(pdf: File): String = parsePDF(new FileInputStream(pdf))

  def parsePDF(pdf: InputStream): String = {
    val document =
      try {
        PDDocument.load(pdf)
      } catch {
        case _: Exception =>
          return "thisfileiscorrupted"
      }

    val text = {
      val tesseract = {
        val tesseract: Tesseract = new Tesseract()
        tesseract.setDatapath("./tessdata")
        tesseract.setLanguage(languages)

        tesseract
      }

      ImageIO.scanForPlugins()
      IIORegistry.getDefaultInstance.registerServiceProvider(new JBIG2ImageReaderSpi)
      val renderer = new PDFRenderer(document)

      var asText = ""
      for (i <- 0 until document.getNumberOfPages) asText += tesseract.doOCR(renderer.renderImage(i, 3.1f, ImageType.GRAY))

      asText
    }

    document.close()
    text
  }
}