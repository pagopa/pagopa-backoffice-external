locals {
  apim_backoffice_external_api = {
    // Backoffice External
    display_name          = "Selfcare Backoffice External Product pagoPA"
    description           = "API for Backoffice External"
    path                  = "backoffice/external"
    subscription_required = true
    service_url           = null
  }
  apim_backoffice_helpdesk_api = {
    // Helpdesk
    display_name          = "Backoffice Helpdesk"
    description           = "API for helpdesk on Backoffice"
    path                  = "backoffice/helpdesk"
    subscription_required = true
    service_url           = null
  }


  host         = "api.${var.apim_dns_zone_prefix}.${var.external_domain}"
  hostname     = var.hostname
}

##############
## Products ##
##############

// Backoffice External user(s)
module "apim_backoffice_external_product" {
  source = "git::https://github.com/pagopa/terraform-azurerm-v3.git//api_management_product?ref=v6.4.1"

  product_id   = "backoffice-external"
  display_name = local.apim_backoffice_external_api.display_name
  description  = local.apim_backoffice_external_api.description

  api_management_name = local.apim.name
  resource_group_name = local.apim.rg

  published             = true
  subscription_required = local.apim_backoffice_external_api.subscription_required
  approval_required     = true
  subscriptions_limit   = 1000

  policy_xml = file("./api_product/backoffice-external/_base_policy.xml")
}

// Helpdesk
module "apim_backoffice_helpdesk_product" {
  source = "git::https://github.com/pagopa/terraform-azurerm-v3.git//api_management_product?ref=v6.4.1"

  product_id   = "backoffice-helpdesk"
  display_name = local.apim_backoffice_helpdesk_api.display_name
  description  = local.apim_backoffice_helpdesk_api.description

  api_management_name = local.apim.name
  resource_group_name = local.apim.rg

  published             = false
  subscription_required = local.apim_backoffice_helpdesk_api.subscription_required
  approval_required     = true
  subscriptions_limit   = 1000

  policy_xml = file("./api_product/backoffice-helpdesk/_base_policy.xml")
}


##############
## Api Vers ##
##############

resource "azurerm_api_management_api_version_set" "api_backoffice_external_api" {

  name                = format("%s-backoffice-external-api", var.env_short)
  resource_group_name = local.apim.rg
  api_management_name = local.apim.name
  display_name        = local.apim_backoffice_external_api.display_name
  versioning_scheme   = "Segment"
}

resource "azurerm_api_management_api_version_set" "api_backoffice_helpdesk_api" {

  name                = format("%s-backoffice-helpdesk-api", var.env_short)
  resource_group_name = local.apim.rg
  api_management_name = local.apim.name
  display_name        = local.apim_backoffice_helpdesk_api.display_name
  versioning_scheme   = "Segment"
}


##############
## OpenApi  ##
##############

module "apim_api_backoffice_external_api_v1" {
  source = "git::https://github.com/pagopa/terraform-azurerm-v3.git//api_management_api?ref=v6.4.1"

  name                  = format("%s-backoffice-external-api", var.env_short)
  api_management_name   = local.apim.name
  resource_group_name   = local.apim.rg
  product_ids           = [module.apim_backoffice_external_product.product_id]
  subscription_required = local.apim_backoffice_external_api.subscription_required
  version_set_id        = azurerm_api_management_api_version_set.api_backoffice_external_api.id
  api_version           = "v1"

  description  = local.apim_backoffice_external_api.description
  display_name = local.apim_backoffice_external_api.display_name
  path         = local.apim_backoffice_external_api.path
  protocols    = ["https"]
  service_url  = local.apim_backoffice_external_api.service_url

  content_format = "openapi"
  content_value = templatefile("./api/backoffice-external/v1/_openapi.json.tpl", {
    host = local.host
  })

  xml_content = templatefile("./api/backoffice-external/v1/_base_policy.xml", {
    hostname = local.hostname
  })
}

module "apim_api_backoffice_helpdesk_api_v1" {
  source = "git::https://github.com/pagopa/terraform-azurerm-v3.git//api_management_api?ref=v6.4.1"

  name                  = format("%s-backoffice-helpdesk-api", var.env_short)
  api_management_name   = local.apim.name
  resource_group_name   = local.apim.rg
  product_ids           = [module.apim_backoffice_helpdesk_product.product_id]
  subscription_required = local.apim_backoffice_helpdesk_api.subscription_required
  version_set_id        = azurerm_api_management_api_version_set.api_backoffice_helpdesk_api.id
  api_version           = "v1"

  description  = local.apim_backoffice_helpdesk_api.description
  display_name = local.apim_backoffice_helpdesk_api.display_name
  path         = local.apim_backoffice_helpdesk_api.path
  protocols    = ["https"]
  service_url  = local.apim_backoffice_helpdesk_api.service_url

  content_format = "openapi"
  content_value = templatefile("./api/backoffice-helpdesk/v1/_openapi.json.tpl", {
    host = local.host
  })

  xml_content = templatefile("./api/backoffice-helpdesk/v1/_base_policy.xml", {
    hostname = local.hostname
  })
}