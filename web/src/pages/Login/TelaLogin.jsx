import LoginUsuario from "../../components/LoginUsuario";
import './TelaLogin.css'
import Logo from '../../images/Logo.png'
import CRmsg1 from '../../images/CRmsg1.png'
import CRmsg2 from '../../images/CRmsg2.png'

export const TelaLogin = () => {
    return (
        <div className="container">
            <div className="atencao">
                <div className="image-slider">
                    <img src={CRmsg1} alt="Mensagem 1" className="message-image1"/>
                    <img src={CRmsg2} alt="Mensagem 2" className="message-image2" />
                </div>
            </div>
            <div className='divLogin'>
                <div className="divImage">
                    <img className='imageLogin' src={Logo} alt="Logo Cartões Rápidos" />
                </div>
                <div className="login">
                    <LoginUsuario />
                </div>
            </div>
        </div>
    );
}