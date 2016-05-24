package com.club.huellas;

/**
 *
 * @author Diego Noble
 */
import com.club.BEANS.Dependiente;
import com.club.BEANS.Funcionario;
import com.club.BEANS.Socio;
import com.club.DAOs.DaoGenerico;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import com.suprema.ufe33.UFMatcherClass;
import com.suprema.ufe33.UFScannerClass;
import imagepanel.ImagePanel;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class BioMini {

    DaoGenerico dao;
    byte[] pImageData;

    private UFScannerClass libScanner = null;
    private UFMatcherClass libMatcher = null;
    private Pointer hMatcher = null;
    byte[] bTemplate = new byte[512];
    int tempsize = 0;
    int nTemplateCnt = 0;
    int[] intTemplateSizeArray = null;
    byte[][] byteTemplateArray = null;
    public final int MAX_TEMPLATE_SIZE = 384;
    private PointerByReference refTemplateArray = null;

    public void iniciar() {
        dao = new DaoGenerico();
        try {
            libScanner = (UFScannerClass) Native.loadLibrary("UFScanner", UFScannerClass.class);
            libMatcher = (UFMatcherClass) Native.loadLibrary("UFMatcher", UFMatcherClass.class);
        } catch (Exception ex) {
            System.out.println("loadLlibrary : UFScanner,UFMatcher fail!!");
            System.out.println("loadLlibrary : UFScanner,UFMatcher fail!!");
            return;
        }
        int nRes = 0;
        nRes = libScanner.UFS_Init();
        initArray(100, 1024);
        if (nRes == 0) {
            System.out.println("UFS_Init() success!!");
            System.out.println("UFS_Init() success,nInitFlag value set 1");
            System.out.println("Scanner Init success!!");
        }
    }

    public void initArray(int nArrayCnt, int nMaxTemplateSize) {
        if (byteTemplateArray != null) {
            byteTemplateArray = null;
        }
        if (intTemplateSizeArray != null) {
            intTemplateSizeArray = null;
        }
        byteTemplateArray = new byte[nArrayCnt][MAX_TEMPLATE_SIZE];
        intTemplateSizeArray = new int[nArrayCnt];
        refTemplateArray = new PointerByReference();
    }

    public Pointer GetCurrentScannerHandle() {
        Pointer hScanner = null;
        int nRes = 0;
        int nNumber = 0;
        try {
            libScanner = (UFScannerClass) Native.loadLibrary("UFScanner", UFScannerClass.class);
            libMatcher = (UFMatcherClass) Native.loadLibrary("UFMatcher", UFMatcherClass.class);
        } catch (Exception ex) {
            System.out.println("loadLlibrary : UFScanner,UFMatcher fail!!");
            System.out.println("loadLlibrary : UFScanner,UFMatcher fail!!");
        }
        PointerByReference refScanner = new PointerByReference();
        IntByReference refScannerNumber = new IntByReference();
// �Ʒ� success!!//
        nRes = libScanner.UFS_GetScannerNumber(refScannerNumber);
        if (nRes == 0) {
            nNumber = refScannerNumber.getValue();
            if (nNumber <= 0) {
                return null;
            }
        } else {
            return null;
        }
        nRes = libScanner.UFS_GetScannerHandle(0, refScanner);
        hScanner = refScanner.getValue();
        if (nRes == 0 && hScanner != null) {
            return hScanner;
        }
        return null;
    }

    public void imagenBD(ImagePanel huellap, Socio socio) {
        try {
            IntByReference refResolution = new IntByReference();
            IntByReference refHeight = new IntByReference();
            IntByReference refWidth = new IntByReference();
            Pointer hScanner = null;
            hScanner = GetCurrentScannerHandle();
            //socio = (Socio) dao.BuscaPorCI("");
            pImageData = socio.getHuella();

            libScanner.UFS_GetCaptureImageBufferInfo(hScanner, refWidth, refHeight, refResolution);
            huellap.drawFingerImage(refWidth.getValue(), refHeight.getValue(), pImageData);
        } catch (Exception ex) {
            Logger.getLogger(BioMini.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void imagenBDFuncionario(ImagePanel huellap, Funcionario funcionario) {
        try {
            IntByReference refResolution = new IntByReference();
            IntByReference refHeight = new IntByReference();
            refHeight.setValue(huellap.getHeight());
            
            IntByReference refWidth = new IntByReference();
            refWidth.setValue(huellap.getWidth());
            Pointer hScanner = null;
            hScanner = GetCurrentScannerHandle();
            //socio = (Socio) dao.BuscaPorCI("");
            pImageData = funcionario.getHuella();
            
            libScanner.UFS_GetCaptureImageBufferInfo(hScanner, refWidth, refHeight, refResolution);
            huellap.drawFingerImage(refWidth.getValue(), refHeight.getValue(), pImageData);
        } catch (Exception ex) {
            Logger.getLogger(BioMini.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void DibujarHuella(ImagePanel huellap) {

        IntByReference refResolution = new IntByReference();
        IntByReference refHeight = new IntByReference();
        IntByReference refWidth = new IntByReference();
        Pointer hScanner = null;
        hScanner = GetCurrentScannerHandle();
        libScanner.UFS_GetCaptureImageBufferInfo(hScanner, refWidth, refHeight, refResolution);
        pImageData = new byte[refWidth.getValue() * refHeight.getValue()];
        libScanner.UFS_GetCaptureImageBuffer(hScanner, pImageData);
        huellap.drawFingerImage(refWidth.getValue(), refHeight.getValue(), pImageData);

    }

    public int CapturaFotoHuella(ImagePanel huellap) {
        int nRes = 0;
        Pointer hScanner = null;
        hScanner = GetCurrentScannerHandle();
        if (hScanner != null) {
        } else {
            return -1;
        }
        nRes = libScanner.UFS_CaptureSingleImage(hScanner);
        if (nRes == 0) {
            DibujarHuella(huellap);
        } else {
            byte[] refErr = new byte[512];
            nRes = libScanner.UFS_GetErrorString(nRes, refErr);
            if (nRes == 0) {
            }
        }
        return nRes;
    }

    public void GuardarHuellaSocio(ImagePanel huellap, Socio socio) {
        try {
            Pointer hScanner = null;
            hScanner = GetCurrentScannerHandle();
            if (hScanner != null) {
                int nRes = libScanner.UFS_ClearCaptureImageBuffer(hScanner);
                nRes = libScanner.UFS_CaptureSingleImage(hScanner);
                if (nRes == 0) {
                    IntByReference refTemplateSize = new IntByReference();
                    IntByReference refTemplateQuality = new IntByReference();
                    try {
                        nRes = libScanner.UFS_Extract(hScanner, bTemplate, refTemplateSize, refTemplateQuality);
                        if (nRes == 0) {
                            int nSelectedValue = 70;
                            if (refTemplateQuality.getValue() < nSelectedValue) {
                                JOptionPane.showMessageDialog(null, "Error al capturar huella, poca calidad. Pruebe nuevamente. ", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            tempsize = refTemplateSize.getValue();
                            System.arraycopy(bTemplate, 0, byteTemplateArray[0], 0, refTemplateSize.getValue());
                            System.out.println(byteTemplateArray[0]);
                            intTemplateSizeArray[0] = refTemplateSize.getValue();
                            System.out.println(intTemplateSizeArray[0]);
                            DibujarHuella(huellap);

                            //String path = "C:/huellas" + socio.getId() + ".jpg";
                            //nRes = libScanner.UFS_SaveCaptureImageBufferToBMP(hScanner, path);
                            socio.setHuella(bTemplate);
                            socio.setTamano(tempsize);
                            //socio.setFotoHuella(path);
                            socio.setCalidad(refTemplateQuality.getValue());
                            dao.Actualizar(socio);
                        } else {
                        }
                    } catch (Exception ex) {
//MsgBox("exception err:"+ex.getMessage());
                    }
                }
            } else {
// scanner pointer null
            }

        } catch (Exception ex) {
            Logger.getLogger(BioMini.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void GuardarHuellaDep(ImagePanel huellap, Dependiente dep) {
        try {
            Pointer hScanner = null;
            hScanner = GetCurrentScannerHandle();
            if (hScanner != null) {
                int nRes = libScanner.UFS_ClearCaptureImageBuffer(hScanner);
                nRes = libScanner.UFS_CaptureSingleImage(hScanner);
                if (nRes == 0) {
                    IntByReference refTemplateSize = new IntByReference();
                    IntByReference refTemplateQuality = new IntByReference();
                    try {
                        nRes = libScanner.UFS_Extract(hScanner, bTemplate, refTemplateSize, refTemplateQuality);
                        if (nRes == 0) {
                            int nSelectedValue = 70;
                            if (refTemplateQuality.getValue() < nSelectedValue) {
                                JOptionPane.showMessageDialog(null, "Error al capturar huella, poca calidad. Pruebe nuevamente. ", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            tempsize = refTemplateSize.getValue();
                            System.arraycopy(bTemplate, 0, byteTemplateArray[0], 0, refTemplateSize.getValue());
                            System.out.println(byteTemplateArray[0]);
                            intTemplateSizeArray[0] = refTemplateSize.getValue();
                            System.out.println(intTemplateSizeArray[0]);
                            DibujarHuella(huellap);

                            dep.setHuella(bTemplate);
                            dep.setTamano(tempsize);
                            dep.setCalidad(refTemplateQuality.getValue());
                            dao.Actualizar(dep);
                        } else {
                        }
                    } catch (Exception ex) {
                    }
                }
            } else {
// scanner pointer null
            }

        } catch (Exception ex) {
            Logger.getLogger(BioMini.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Socio BuscarHuellaSocioTitular(ImagePanel huellap, List<Socio> socios) {
        Socio socioEncontrado = new Socio();
        socioEncontrado = null;
        PointerByReference refMatcher = new PointerByReference();
        int nRes = libMatcher.UFM_Create(refMatcher);
        if (nRes == 0) {
            hMatcher = refMatcher.getValue();
        }
        int nSelectedIdx = 0;
        if (nSelectedIdx == -1) {
        }
        Pointer hScanner = null;
        hScanner = GetCurrentScannerHandle();
        if (hScanner == null) {
        }
        libScanner.UFS_ClearCaptureImageBuffer(hScanner);
        nRes = libScanner.UFS_CaptureSingleImage(hScanner);
        if (nRes != 0) {
        }
        byte[] aTemplate = new byte[512];
        PointerByReference refError;
        IntByReference refTemplateSize = new IntByReference();
        IntByReference refTemplateQuality = new IntByReference();
        IntByReference refVerify = new IntByReference();
        nRes = libScanner.UFS_Extract(hScanner, aTemplate, refTemplateSize, refTemplateQuality);
        DibujarHuella(huellap);
        if (nRes == 0) {
            try {

                int i = 0;
                int id = 0;

                for (Socio socio : socios) {
                    nRes = libMatcher.UFM_Verify(hMatcher, aTemplate, refTemplateSize.getValue(), socio.getHuella(), socio.getTamano(), refVerify);//byte[][]
                    if (refVerify.getValue() == 1) {
                        socioEncontrado = socio;
                        break;
                    }
                }

            } catch (Exception ex) {
                System.out.println(ex);
                ex.printStackTrace();
            }
        } else {
        }
        return socioEncontrado;
    }

    public Dependiente BuscarHuellaDep(ImagePanel huellap, List<Dependiente> dependientes) {
        Dependiente depEncontrado = new Dependiente();
        depEncontrado = null;
        PointerByReference refMatcher = new PointerByReference();
        int nRes = libMatcher.UFM_Create(refMatcher);
        if (nRes == 0) {
            hMatcher = refMatcher.getValue();
        }
        int nSelectedIdx = 0;
        if (nSelectedIdx == -1) {
        }
        Pointer hScanner = null;
        hScanner = GetCurrentScannerHandle();
        if (hScanner == null) {
        }
        libScanner.UFS_ClearCaptureImageBuffer(hScanner);
        nRes = libScanner.UFS_CaptureSingleImage(hScanner);
        if (nRes != 0) {
        }
        byte[] aTemplate = new byte[512];
        PointerByReference refError;
        IntByReference refTemplateSize = new IntByReference();
        IntByReference refTemplateQuality = new IntByReference();
        IntByReference refVerify = new IntByReference();
        nRes = libScanner.UFS_Extract(hScanner, aTemplate, refTemplateSize, refTemplateQuality);
        DibujarHuella(huellap);
        if (nRes == 0) {
            try {

                int i = 0;
                int id = 0;

                for (Dependiente dependiente : dependientes) {
                    nRes = libMatcher.UFM_Verify(hMatcher, aTemplate, refTemplateSize.getValue(), dependiente.getHuella(), dependiente.getTamano(), refVerify);//byte[][]
                    if (refVerify.getValue() == 1) {
                        depEncontrado = dependiente;
                        break;
                    }
                }

            } catch (Exception ex) {
                System.out.println(ex);
                ex.printStackTrace();
            }
        } else {
        }
        return depEncontrado;
    }

    public Funcionario BuscarHuellaFuncionario(ImagePanel huellap, List<Funcionario> funcionarios) {
        Funcionario funcionarioEncontrado = new Funcionario();
        funcionarioEncontrado = null;
        PointerByReference refMatcher = new PointerByReference();
        int nRes = libMatcher.UFM_Create(refMatcher);
        if (nRes == 0) {
            hMatcher = refMatcher.getValue();
        }
        int nSelectedIdx = 0;
        if (nSelectedIdx == -1) {
        }
        Pointer hScanner = null;
        hScanner = GetCurrentScannerHandle();
        if (hScanner == null) {
        }
        libScanner.UFS_ClearCaptureImageBuffer(hScanner);
        nRes = libScanner.UFS_CaptureSingleImage(hScanner);
        if (nRes != 0) {
        }
        byte[] aTemplate = new byte[512];
        PointerByReference refError;
        IntByReference refTemplateSize = new IntByReference();
        IntByReference refTemplateQuality = new IntByReference();
        IntByReference refVerify = new IntByReference();
        nRes = libScanner.UFS_Extract(hScanner, aTemplate, refTemplateSize, refTemplateQuality);
        DibujarHuella(huellap);
        if (nRes == 0) {
            try {

                int i = 0;
                int id = 0;

                for (Funcionario funcionario : funcionarios) {
                    nRes = libMatcher.UFM_Verify(hMatcher, aTemplate, refTemplateSize.getValue(), funcionario.getHuella(), funcionario.getTamano(), refVerify);//byte[][]
                    if (refVerify.getValue() == 1) {
                        funcionarioEncontrado = funcionario;
                        break;
                    }
                }

            } catch (Exception ex) {
                System.out.println(ex);
                ex.printStackTrace();
            }
        } else {
        }
        return funcionarioEncontrado;
    }

    public void GuardarHuellaFuncionario(ImagePanel huellap, Funcionario funcionario) {
        try {
            Pointer hScanner = null;
            hScanner = GetCurrentScannerHandle();
            if (hScanner != null) {
                int nRes = libScanner.UFS_ClearCaptureImageBuffer(hScanner);
                nRes = libScanner.UFS_CaptureSingleImage(hScanner);
                if (nRes == 0) {
                    IntByReference refTemplateSize = new IntByReference();
                    IntByReference refTemplateQuality = new IntByReference();
                    try {
                        nRes = libScanner.UFS_Extract(hScanner, bTemplate, refTemplateSize, refTemplateQuality);
                        if (nRes == 0) {
                            int nSelectedValue = 70;
                            if (refTemplateQuality.getValue() < nSelectedValue) {
                                JOptionPane.showMessageDialog(null, "Error al capturar huella, poca calidad. Pruebe nuevamente. ", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            tempsize = refTemplateSize.getValue();
                            System.arraycopy(bTemplate, 0, byteTemplateArray[0], 0, refTemplateSize.getValue());
                            System.out.println(byteTemplateArray[0]);
                            intTemplateSizeArray[0] = refTemplateSize.getValue();
                            System.out.println(intTemplateSizeArray[0]);
                            DibujarHuella(huellap);

                            //String path = "C:/huellas" + socio.getId() + ".jpg";
                            //nRes = libScanner.UFS_SaveCaptureImageBufferToBMP(hScanner, path);
                            funcionario.setHuella(bTemplate);
                            funcionario.setTamano(tempsize);
                            //socio.setFotoHuella(path);
                            funcionario.setCalidad(refTemplateQuality.getValue());
                            dao.Actualizar(funcionario);
                        } else {
                        }
                    } catch (Exception ex) {
//MsgBox("exception err:"+ex.getMessage());
                    }
                }
            } else {
// scanner pointer null
            }

        } catch (Exception ex) {
            Logger.getLogger(BioMini.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
