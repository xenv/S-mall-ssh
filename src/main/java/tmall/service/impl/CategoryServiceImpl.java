package tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tmall.pojo.Category;
import tmall.service.CategoryService;
import tmall.service.ProductService;

import java.util.List;

/**
 * @see CategoryService
 */
@Service
@Transactional
public class CategoryServiceImpl extends BaseServiceImpl<Category>  implements CategoryService  {

}