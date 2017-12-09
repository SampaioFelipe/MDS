package br.ufscar.dc.mds.curumim.utils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public abstract class TipOfTheDay {

    public static String getTipOfTheDay() {

        return getTipOfTheDayOfMonthOffline();
    }

    public static String getTipOfTheDayOfMonthOffline() {
        List<String> tipsOfTheDay = Arrays.asList(
                "Um dos maiores dilemas para os pais de recém-nascidos têm são as cólicas. Sim, a palavra vai no plural, porque ela não acontece por um único motivo! Infelizmente, essa dor é normal e costuma ocorrer entre duas e três semanas de vida e parar por volta dos três ou quatro meses.",
                "Quando o bebê começa a chorar e nada resolve, alguns pais recorrem a alguns métodos para minimizar as dores. Algo que pode resolver e ainda é gostoso de fazer, é uma massagem caseira!",
                "Cuidar do umbigo do recém-nascido é algo essencial, mas que muitos pais ainda sentem medo! O coto umbilical não é esse terror todo, não precisa ter tanto receio. Ele não dói, porque não tem nervos, é só um pedaço da pele da mãe.",
                "Um dos hábitos que as mães de recém-nascidos precisam adotar com os bebês é o famoso banho de sol. Ele é essencial para ativar a vitamina D, recebida pelo leite materno, no corpo da criança.",
                "Não precisa se preocupar: a moleira não é tão frágil assim. Mesmo não sendo tão frágil quanto parece, é preciso ficar atenta às mudanças na moleira do bebê até que elas se fechem.",
                "Os bebés ocupam um quinto do tempo em que estão acordados a observar fixamente. Eles são curiosos sobre o ambiente que os rodeia. As fotos na parede e as atividades normais da família são naturalmente fascinantes. Dê ao bebé brinquedos seguros e objetos para explorar. Varie os brinquedos que lhe dá para manter um ambiente com novidade.",
                "Antes da amamentação, as narinas devem ser higienizadas com soros fisiológicos em spray específicos para bebês, pois eles têm o jato mais suave.",
                "Os pais devem incentivar a criança a brincar, explorar e aprender, isso faz parte dos bons cuidados com crianças.",
                "O filho acaba de sair de um ambiente de pura paz, o útero materno. Por isso, a casa deve ser igualmente tranquila e silenciosa nos primeiros dias de vida. Especialmente durante a amamentação e na hora de dormir.",
                "A boa nutrição é muito importante para a saúde infantil, especialmente nos primeiros anos. A nutrição da criança, durante os primeiros cinco anos de vida, terá um efeito significativo no seu desenvolvimento mental e físico.",
                "Converse com a criança durante a refeição – descreva a comida, a situação, as pessoas ao redor e diga como ela está comendo bem. Mesmo que a criança não possa responder, ela estará aprendendo os nomes e os significados das coisas.",
                "A casa é cheia de perigos para a criança. Você não pode impedi-la de ser curiosa e ativa, mas pode criar um ambiente seguro e livre do perigo.",
                "Cuidado com o armarinho que contém remédios, giletes, cosméticos, entre outros, deixe-o fechado e esses materiais longe do alcance da criança. Segure firmemente a criança ao banhá-la e vesti-la. ",
                "Não permita à criança colocar sabão na boca e nunca deixe a criança pequena sozinha na banheira, mesmo por um minuto. Ela pode se afogar em menos de 15 cm de água.",
                "As pesquisas mostram que os serviços de uma creche e a educação de qualidade pode ter uma influência positiva no desenvolvimento das crianças e na sua preparação para a escola, oferecendo-lhes experiências educacionais e sociais preciosas.",
                "É especialmente importante despertar na criança entusiasmo e motivação no cuidado dos dentes. Está nas mãos dos pais ser um bom exemplo, tanto no que diz respeito à escovagem dos dentes como às visitas ao dentista.",
                "Não carregue as crianças no colo enquanto mexe panelas no fogão ou manipula líquidos quentes. Até um simples cafezinho pode provocar graves queimaduras na pele de um bebê.",
                "É essencial ensinar a criança a lavar as mãos antes e depois das refeições, assim como depois de usar o WC.",
                "Cuidar do cabelo de uma criança é outro hábito infantil de elevada importância e que passa não só por manter o cabelo limpo e arranjado, como vigiar e assim prevenir o aparecimento de parasitas.",
                "É fundamental que a criança contribua para a sua própria saúde mas também para a dos outros. Por isso, é importante que aprenda a evitar o contágio em caso de constipações, gripes ou outras doenças transmissíveis por via aérea, aprendendo a gerir corretamente o contacto com os outros em caso de doença.",
                "Os brinquedos das crianças devem ser cuidadosamente limpos, com uma regularidade maior quanto menor for a idade da criança, uma vez que as suas defesas são mais frágeis.",
                "Em dias secos é importante hidratar a pele, evitando banhos muito quentes, pois provocam o ressecamento da mesma, e usar creme hidratante e protetor labial, de acordo com a orientação do Pediatra da criança.",
                "Grande parte das interações com as crianças envolvem refeições, banho, tarefas escolares e troca de roupas. Reserve alguns minutos do dia para brincar, jogar, assistir a algum programa que ambos gostem ou fazer outras atividades que não tenham caráter obrigatório e de rotina.",
                "Os especialistas dizem isso a todo momento. A água faz muito bem ao organismo, ela atua em todas as funções do corpo (circulação, digestão, excreção) e além de hidratá-lo permite também que a temperatura seja mantida.",
                "Nenhum alimento deve ser proibido ou considerado vilão na alimentação de uma criança. O doce pode fazer parte de um momento social, nas sobremesas ou como parte do lanche.",
                "Faça acordos com a criança, mas dê o exemplo. Se não quiser que seu filho coma doce em um dia, não coma também. E preste muita atenção nas refeições em família: todo mundo precisa ter um prato balanceado para dar o exemplo.",
                "Até o primeiro ano de vida as atividades são básicas. A criança pode brincar livremente em espaços seguros ou praticar natação. Entre os 3 e os 5 anos, as atividades devem ser aquelas que melhoram o desenvolvimento da coordenação motora e estimulam a criatividade.",
                "O quarto mais ensolarado deve ser o quarto do bebê. Posicione a cama de modo que receba luz solar. Um quarto arejado diariamente por, no mínimo, duas horas diárias, é imprescindível para a saúde das vias aéreas.",
                "Bebês e crianças são curiosos por natureza, por isso querem explorar e mexer em tudo e sabemos que nem sempre conseguimos estar ao lado deles supervisionando tudo que fazem. Por isso principalmente na cozinha, área de serviço e banheiros o ideal é ter travas nos armários que estiverem ao alcance da criança, pois objetos como panelas, tábuas, baldes, entre outros, podem cair.",
                "Cuidar e educar significa compreender que o espaço/tempo em que a criança vive exige seu esforço particular e a mediação dos adultos como forma de proporcionar ambientes que estimulem a curiosidade com consciência e responsabilidade.",
                "Para compreender a relação entre cuidado e aprendizagem, é preciso observar como as crianças vivenciam os procedimentos e respondem às atitudes dos professores ou de familiares ao cuidar delas, ao ensiná-las a cuidarem de si e nas brincadeiras com seus pares. "
        );

        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        return tipsOfTheDay.get(day - 1);
    }
}
