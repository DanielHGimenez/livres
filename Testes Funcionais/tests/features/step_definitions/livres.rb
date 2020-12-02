# Está função está correlacionada com o arquivo teste.feature e basicamente ela vai acessar o site com a url abaixo, caso a url mude é so trocar.
Dado('Entro no site livres da baixada santista') do
    visit 'http://livresbs.ga/'
    sleep(2)
end
# Aqui basicamente vai escolher os produtos e a quantidade,o find está buscando por id que é relacionado com id da cotação no banco de dados e após isso coloquei a classe para identifcar
# e no set ele coloca a quantidade de produtos
Quando('Escolho os produtos') do
    find_link(href: '/produtos').click
    find('tr[id="1"] .qtd-produto-input').set('1')
    
    find('tr[id="2"] .qtd-produto-input').set('1')
    

    find('.btn-primary').click
    
   sleep(2)
end
# Aqui eu coloquei uma Gem do ruby chamada Faker para gerar dados aleatorios então fica bem mais facil de realizar manutenção, além não criar conflito no banco de dados na hora de testar
Entao('Faço uma compra') do
    find('.btn-primary').click
    
    find('#nome').set(Faker::Name.name)
    find('#cep').set(Faker::Address.zip_code)
    find('#estado').set('SP')
    find('#cidade').set(Faker::Address.city )
    find('#bairro').set(Faker::Address.community)
    find('#endereco').set(Faker::Address.street_name)
    find('#numero').set(Faker::Address.building_number)
    find('#complemento').set('Casa')
    
    find('.fa-share').click
    
    select 'dinheiro', from: 'metodo'
    
    select 'presencial', from: 'meio'
    
    find('#btn-continuar-pagamento').click
    
    click_button('Finalizar Pedido')
    have_text('Pedido enviado para a análise!')  
end