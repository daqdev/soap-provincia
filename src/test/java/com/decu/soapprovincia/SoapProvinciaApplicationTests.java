package com.decu.soapprovincia;

import com.decu.soapprovincia.ws.client.generated.*;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Array;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SoapProvinciaApplicationTests {

    static ServiceSoap service;
    static CmpDatos datosOK;
    static CmpDatos datosNOK_pVta;
    static CmpAuthRequest authOK;

    @BeforeAll
    static void setup() {
        Service soap = new Service();
        service = soap.getServiceSoap();
        authOK = authOK();
        datosOK = cmpOK();
        datosNOK_pVta = cmpNOK_PtoVta();
    }


    @Test
    public void baseTest() {
        assertEquals("OK", service.comprobanteDummy().getAppServer());
        assertEquals("OK", service.comprobanteDummy().getAuthServer());
        assertEquals("OK", service.comprobanteDummy().getDbServer());
    }

    @Test
    public void comprobanteConstatarOK() {
        CmpResponse response = service.comprobanteConstatar(authOK, datosOK);
        System.out.println(response.getResultado());
        assertEquals("A", response.getResultado());
        response.getErrors().getErr().stream().forEach(x -> System.out.println(x.getCode() + ":" + x.getMsg()));
        response.getObservaciones().getObs().stream().forEach(x -> System.out.println(x.getCode() + ":" + x.getMsg()));

    }

    @Test
    public void comprobanteConstatarNOK_PVta() {
        CmpResponse response = service.comprobanteConstatar(authOK, datosNOK_pVta);
        System.out.println(response.getResultado());
        response.getErrors().getErr().stream().forEach(x -> System.out.println(x.getCode() + ":" + x.getMsg()));
        assertEquals("A", response.getResultado());
    }

    @Test
    public void comprobantesModalidadConsultarOK() {
        FacModTipoResponse response = service.comprobantesModalidadConsultar(authOK);
        response.getErrors().getErr().stream().forEach(x -> System.out.println(x.getCode() + ":" + x.getMsg()));
        assertNotNull(response.getResultGet());
        response.getResultGet().getFacModTipo().stream().forEach(x -> System.out.println(x.getCod() + ":" + x.getDesc()));

    }

    @Test
    public void comprobantesTipoConsultarOK() {
        CbteTipoResponse response = service.comprobantesTipoConsultar(authOK);
        response.getErrors().getErr().stream().forEach(x -> System.out.println(x.getCode() + ":" + x.getMsg()));
        assertNotNull(response.getResultGet());
        response.getResultGet().getCbteTipo().stream().forEach(x -> System.out.println(x.getId() + ":" + x.getDesc()));

    }

    @Test
    public void documentosTipoConsultarOK() {
        DocTipoResponse response = service.documentosTipoConsultar(authOK);
        response.getErrors().getErr().stream().forEach(x -> System.out.println(x.getCode() + ":" + x.getMsg()));
        assertNotNull(response.getResultGet());
        response.getResultGet().getDocTipo().stream().forEach(x -> System.out.println(x.getId() + ":" + x.getDesc()));

    }

    @Test
    public void opcionalesTipoConsultar() {
        OpcionalTipoResponse response = service.opcionalesTipoConsultar(authOK);
        response.getErrors().getErr().stream().forEach(x -> System.out.println(x.getCode() + ":" + x.getMsg()));
        assertNotNull(response.getResultGet());
        response.getResultGet().getOpcionalTipo().stream().forEach(x -> System.out.println(x.getId() + ":" + x.getDesc()));

    }

    private static CmpAuthRequest authOK() {
        authOK = new CmpAuthRequest();
        authOK.setCuit(300000000007L);
        authOK.setSign("11111111");
        authOK.setToken("111");
        return authOK;
    }

    private static CmpDatos cmpOK() {
        CmpDatos cmpReq = new CmpDatos();
        cmpReq.setCbteModo("CAE");
        cmpReq.setCuitEmisor(20000000001L);
        cmpReq.setPtoVta(1);
        cmpReq.setCbteTipo(1);
        cmpReq.setCbteNro(2);
        cmpReq.setCbteFch("20101014");
        cmpReq.setImpTotal(300.8);
        cmpReq.setCodAutorizacion("60428000005029");
        cmpReq.setDocTipoReceptor("80");
        cmpReq.setDocNroReceptor("300000000007");
        return cmpReq;
    }

    private static CmpDatos cmpNOK_PtoVta() {
        CmpDatos cmpReq = new CmpDatos();
        cmpReq.setCbteModo("CAE");
        cmpReq.setCuitEmisor(20000000001L);
        cmpReq.setPtoVta(999);
        cmpReq.setCbteTipo(1);
        cmpReq.setCbteNro(2);
        cmpReq.setCbteFch("20101014");
        cmpReq.setImpTotal(300.8);
        cmpReq.setCodAutorizacion("60428000005029");
        cmpReq.setDocTipoReceptor("80");
        cmpReq.setDocNroReceptor("300000000007");
        return cmpReq;
    }
}
