locals {
  product = "${var.prefix}-${var.env_short}"

  apim = {
    name                   = "${local.product}-apim"
    rg                     = "${local.product}-api-rg"
    bo_external_product_id = "selfcare-bo-external"
    bo_helpdesk_product_id = "selfcare-bo-helpdesk"
  }
}

