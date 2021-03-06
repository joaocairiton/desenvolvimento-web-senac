/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

/**
 *
 * @author joaoc
 */
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Component
@Scope("session")
public class AcessoUsuarioControle {

    String nomeUsuario;
    String senha;
    Boolean logado = false;

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public String login(HttpSession session) {
        session.invalidate();
        return "redirect:/login.xhtml ";
    }

    public String entrar() {
        if ("153624".equals(senha)) {
            logado = true;
            return "redirect:/index.xhtml";
        } else {
            FacesMessage msgJsf = new FacesMessage("Senha incorreta!");
            msgJsf.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msgJsf);
        }
        return null;
    }

    public Boolean getLogado() {
        return logado;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
