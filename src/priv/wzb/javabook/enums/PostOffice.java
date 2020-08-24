package priv.wzb.javabook.enums;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-21 16:31
 **/

public class PostOffice {
    enum MailHandler {
        GENERAL_DELIVERY {
            @Override
            boolean handle(Mail m) {
                switch(m.generalDelivery) {
                    case YES:
                        System.out.println(
                                "Using general delivery for " + m);
                        return true;
                    default: return false;
                }
            }
        },
        MACHINE_SCAN {
            @Override
            boolean handle(Mail m) {
                switch(m.scannability) {
                    case UNSCANNABLE: return false;
                    default:
                        switch(m.address) {
                            case INCORRECT: return false;
                            default:
                                System.out.println(
                                        "Delivering "+ m + " automatically");
                                return true;
                        }
                }
            }
        },
        VISUAL_INSPECTION {
            @Override
            boolean handle(Mail m) {
                switch(m.readability) {
                    case ILLEGIBLE: return false;
                    default:
                        switch(m.address) {
                            case INCORRECT: return false;
                            default:
                                System.out.println(
                                        "Delivering " + m + " normally");
                                return true;
                        }
                }
            }
        },
        RETURN_TO_SENDER {
            @Override
            boolean handle(Mail m) {
                switch(m.returnAddress) {
                    case MISSING: return false;
                    default:
                        System.out.println(
                                "Returning " + m + " to sender");
                        return true;
                }
            }
        };
        abstract boolean handle(Mail m);
    }
    static void handle(Mail m) {
        for(MailHandler handler : MailHandler.values())
            if(handler.handle(m))
                return;
        System.out.println(m + " is a dead letter");
    }
    public static void main(String[] args) {
        for(Mail mail : Mail.generator(10)) {
            System.out.println(mail.details());
            handle(mail);
            System.out.println("*****");
        }
    }

}
