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
    display_name          = "Selfcare Backoffice Helpdesk Product pagoPA"
    description           = "API for Backoffice Helpdesk"
    path                  = "backoffice/helpdesk"
    subscription_required = true
    service_url           = null
  }


  host     = "api.${var.apim_dns_zone_prefix}.${var.external_domain}"
  hostname = var.hostname
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
  product_ids           = [local.apim.bo_external_product_id]
  subscription_required = local.apim_backoffice_external_api.subscription_required
  version_set_id        = azurerm_api_management_api_version_set.api_backoffice_external_api.id
  api_version           = "v1"

  description  = local.apim_backoffice_external_api.description
  display_name = local.apim_backoffice_external_api.display_name
  path         = local.apim_backoffice_external_api.path
  protocols    = ["https"]
  service_url  = local.apim_backoffice_external_api.service_url

  content_format = "openapi"
  content_value  = file("../openapi/openapi_backoffice_external.json")

  xml_content = templatefile("./policy/_base_policy.xml", {
    hostname = local.hostname
  })

  api_operation_policies = [
    {
      operation_id = "getBrokerInstitutions",
      xml_content = templatefile("./policy/_get_broker_institutions_policy.xml", {
        hostname = local.hostname
      })
    },
  ]
}

module "apim_api_backoffice_helpdesk_api_v1" {
  source = "git::https://github.com/pagopa/terraform-azurerm-v3.git//api_management_api?ref=v6.4.1"

  name                  = format("%s-backoffice-helpdesk-api", var.env_short)
  api_management_name   = local.apim.name
  resource_group_name   = local.apim.rg
  product_ids           = [local.apim.bo_helpdesk_product_id]
  subscription_required = local.apim_backoffice_helpdesk_api.subscription_required
  version_set_id        = azurerm_api_management_api_version_set.api_backoffice_helpdesk_api.id
  api_version           = "v1"

  description  = local.apim_backoffice_helpdesk_api.description
  display_name = local.apim_backoffice_helpdesk_api.display_name
  path         = local.apim_backoffice_helpdesk_api.path
  protocols    = ["https"]
  service_url  = local.apim_backoffice_helpdesk_api.service_url

  content_format = "openapi"
  content_value  = file("../openapi/openapi_backoffice_helpdesk.json")

  xml_content = templatefile("./policy/_base_policy.xml", {
    hostname = local.hostname
  })
}
